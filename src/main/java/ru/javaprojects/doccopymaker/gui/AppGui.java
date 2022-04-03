package ru.javaprojects.doccopymaker.gui;

import ru.javaprojects.doccopymaker.core.copycreator.DocumentCopyCreator;
import ru.javaprojects.doccopymaker.core.pathmaker.DocumentPathMaker;
import ru.javaprojects.doccopymaker.core.properties.Directories;
import ru.javaprojects.doccopymaker.core.reader.ConsignmentNoteReader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class AppGui {
    private JFrame appFrame;
    private JLabel chooseCNoteLabel;
    private JTextField chooseCNoteField;
    private JButton chooseCNoteButton;
    private JLabel chooseDestinationLabel;
    private JTextField chooseDestinationField;
    private JButton chooseDestinationButton;
    private JLabel copyInfoLabel;
    private JTextArea copyInfoArea;
    private JLabel errorInfoLabel;
    private JTextArea errorInfoArea;
    private JLabel copyDocumentLabel;
    private JLabel progressLabel;
    private JButton startButton;
    private JFileChooser cNoteFileChooser;
    private JFileChooser destinationDirectoryChooser;
    private Path destinationDirectory = Directories.DEFAULT_DESTINATION_DIRECTORY;
    private Path cNotePath;

    public void makeGui() {
        appFrame = new JFrame("Electronic Document Copy Maker");
        appFrame.setSize(1024, 750);
        appFrame.setLayout(null);
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font courierNew = new Font("Courier New", Font.BOLD, 18);

        chooseCNoteLabel = new JLabel("Выберите накладную, опись или таблицу:");
        chooseCNoteLabel.setFont(courierNew);
        chooseCNoteLabel.setBounds(50, 30, 500, 20);

        chooseCNoteField = new JTextField();
        chooseCNoteField.setFont(courierNew);
        chooseCNoteField.setDisabledTextColor(Color.BLACK);
        chooseCNoteField.setBounds(50, 50, 500, 30);
        chooseCNoteField.setEnabled(false);

        cNoteFileChooser = getCNoteFileChooser();

        chooseCNoteButton = new JButton("Выбрать");
        chooseCNoteButton.setBounds(560, 50, 95, 30);
        chooseCNoteButton.addActionListener(event -> {
            int chooseResult = cNoteFileChooser.showOpenDialog(appFrame);
            if (chooseResult == JFileChooser.APPROVE_OPTION) {
                cNotePath = cNoteFileChooser.getSelectedFile().toPath();
                chooseCNoteField.setText(cNotePath.getFileName().toString());
            }
        });

        chooseDestinationLabel = new JLabel("Выберите папку, куда будут записаны копии:");
        chooseDestinationLabel.setFont(courierNew);
        chooseDestinationLabel.setBounds(50, 100, 500, 20);

        chooseDestinationField = new JTextField();
        chooseDestinationField.setFont(courierNew);
        chooseDestinationField.setDisabledTextColor(Color.BLACK);
        chooseDestinationField.setText(destinationDirectory.toString());
        chooseDestinationField.setBounds(50, 120, 500, 30);
        chooseDestinationField.setEnabled(false);

        destinationDirectoryChooser = getDestinationDirectoryChooser();

        chooseDestinationButton = new JButton("Выбрать");
        chooseDestinationButton.setBounds(560, 120, 95, 30);
        chooseDestinationButton.addActionListener(event -> {
            int chooseResult = destinationDirectoryChooser.showOpenDialog(appFrame);
            if (chooseResult == JFileChooser.APPROVE_OPTION) {
                destinationDirectory = destinationDirectoryChooser.getSelectedFile().toPath();
                chooseDestinationField.setText(destinationDirectory.toString());
            }
        });

        copyInfoLabel = new JLabel();
        copyInfoLabel.setFont(courierNew);
        copyInfoLabel.setBounds(50, 190, 420, 20);

        copyInfoArea = new JTextArea(10, 20);
        copyInfoArea.setEnabled(false);
        copyInfoArea.setLineWrap(true);
        copyInfoArea.setFont(courierNew);
        copyInfoArea.setDisabledTextColor(Color.BLACK);
        JScrollPane copyInfoScroller = new JScrollPane(copyInfoArea);
        copyInfoScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        copyInfoScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        copyInfoScroller.setBounds(50, 210, 420, 300);

        errorInfoLabel = new JLabel();
        errorInfoLabel.setFont(courierNew);
        errorInfoLabel.setBounds(535, 190, 420, 20);

        errorInfoArea = new JTextArea(10, 20);
        errorInfoArea.setEnabled(false);
        errorInfoArea.setLineWrap(true);
        errorInfoArea.setFont(courierNew);
        errorInfoArea.setDisabledTextColor(Color.RED);
        JScrollPane errorInfoScroller = new JScrollPane(errorInfoArea);
        errorInfoScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        errorInfoScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        errorInfoScroller.setBounds(535, 210, 420, 300);

        copyDocumentLabel = new JLabel();
        copyDocumentLabel.setFont(courierNew);
        copyDocumentLabel.setBounds(360, 530, 500, 20);

        progressLabel = new JLabel();
        progressLabel.setFont(courierNew);
        progressLabel.setBounds(360, 560, 400, 20);

        startButton = new JButton("Начать");
        startButton.setBounds(860, 610, 95, 40);
        startButton.addActionListener(event -> {
            if (Objects.isNull(cNotePath)) {
                JOptionPane.showMessageDialog(appFrame, "Вы не выбрали накладную, опись или таблицу!", "Ошибка", ERROR_MESSAGE);
            } else {
                startButton.setEnabled(false);
                SwingWorker<String, Void> makeCopiesWorker= new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() {
                        makeCopies();
                        return null;
                    }
                };
                makeCopiesWorker.execute();
            }
        });

        appFrame.add(chooseCNoteLabel);
        appFrame.add(chooseCNoteField);
        appFrame.add(chooseCNoteButton);
        appFrame.add(chooseDestinationLabel);
        appFrame.add(chooseDestinationField);
        appFrame.add(chooseDestinationButton);
        appFrame.add(copyInfoLabel);
        appFrame.add(copyInfoScroller);
        appFrame.add(errorInfoLabel);
        appFrame.add(errorInfoScroller);
        appFrame.add(copyDocumentLabel);
        appFrame.add(progressLabel);
        appFrame.add(startButton);
        appFrame.setVisible(true);
    }

    private JFileChooser getCNoteFileChooser() {
        JFileChooser cNoteFileChooser = new JFileChooser(Directories.DEFAULT_C_NOTE_DIRECTORY.toString());
        cNoteFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        cNoteFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String fileName = f.getName().toLowerCase();
                return fileName.endsWith(".doc") || fileName.endsWith(".docx");
            }

            @Override
            public String getDescription() {
                return "MS Word documents (*.doc, *.docx)";
            }
        });
        return cNoteFileChooser;
    }

    private JFileChooser getDestinationDirectoryChooser() {
        JFileChooser destinationDirectoryChooser = new JFileChooser(Directories.DEFAULT_DESTINATION_DIRECTORY.toString());
        destinationDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return destinationDirectoryChooser;
    }

    private void makeCopies() {
        SwingUtilities.invokeLater(() -> {
            copyInfoArea.setText("");
            errorInfoArea.setText("");
            errorInfoLabel.setText("");
            copyInfoLabel.setText("Ход копирования:");
        });
        ConsignmentNoteReader noteReader = ConsignmentNoteReader.getReader(cNotePath.toString());
        List<String> decimalNumbers = noteReader.getDecimalNumbers();
        DocumentPathMaker pathMaker = new DocumentPathMaker();
        DocumentCopyCreator copyCreator = new DocumentCopyCreator(destinationDirectory);
        AtomicInteger copyCounter = new AtomicInteger(0);
        SwingUtilities.invokeLater(() -> progressLabel.setText(String.format("Скопировано документов: %d/%d", copyCounter.get(), decimalNumbers.size())));
        decimalNumbers.forEach(decimalNumber -> {
            try {
                Thread.sleep(500);
                SwingUtilities.invokeLater(() -> copyDocumentLabel.setText("Копируется документ: " + decimalNumber));
                Path documentPath = pathMaker.makePath(decimalNumber);
                copyCreator.createCopy(documentPath);
                String message = String.format("%-29sУспешно\n", decimalNumber);
                SwingUtilities.invokeLater(() -> {
                    copyInfoArea.append(message);
                    progressLabel.setText(String.format("Скопировано документов: %d/%d", copyCounter.incrementAndGet(), decimalNumbers.size()));
                });
            } catch (Exception e) {
                String message = String.format("%-29sНеудача\n", decimalNumber);
                SwingUtilities.invokeLater(() -> {
                    errorInfoLabel.setText("Не удалось создать копии:");
                    copyInfoArea.append(message);
                    errorInfoArea.append(decimalNumber + "\n");
                });
            }
        });
        SwingUtilities.invokeLater(() -> {
            copyDocumentLabel.setText("Копирование завершено");
            errorInfoArea.setCaretPosition(0);
        });
        startButton.setEnabled(true);
    }
}