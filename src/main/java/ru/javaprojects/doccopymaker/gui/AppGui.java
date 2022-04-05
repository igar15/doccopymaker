package ru.javaprojects.doccopymaker.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javaprojects.doccopymaker.core.copycreator.DocumentCopyCreator;
import ru.javaprojects.doccopymaker.core.pathmaker.DocumentPathMaker;
import ru.javaprojects.doccopymaker.core.properties.Directories;
import ru.javaprojects.doccopymaker.core.properties.DocSpecifiers;
import ru.javaprojects.doccopymaker.core.reader.ConsignmentNoteReader;
import ru.javaprojects.doccopymaker.core.reader.UnsupportedConsignmentNoteException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class AppGui {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private JFrame appFrame;
    private Font appFont;
    private JLabel chooseCNoteLabel;
    private JTextField chooseCNoteField;
    private JButton chooseCNoteButton;
    private JLabel chooseDestinationLabel;
    private JTextField chooseDestinationField;
    private JButton chooseDestinationButton;
    private JLabel copyInfoLabel;
    private JTextArea copyInfoArea;
    private JScrollPane copyInfoScroller;
    private JLabel errorInfoLabel;
    private JTextArea errorInfoArea;
    private JScrollPane errorInfoScroller;
    private JLabel copyDocumentLabel;
    private JLabel progressLabel;
    private JButton startButton;
    private JFileChooser cNoteFileChooser;
    private JFileChooser destinationDirectoryChooser;
    private Path destinationDirectory;
    private Path cNotePath;

    public void makeGui() {
        createAppFont();
        createCNoteLabel();
        createCNoteField();
        createCNoteFileChooser();
        createChooseCNoteButton();
        createChooseDestinationLabel();
        createChooseDestinationField();
        createDestinationDirectoryChooser();
        createChooseDestinationButton();
        createCopyInfoLabel();
        createCopyInfoArea();
        createErrorInfoLabel();
        createErrorInfoArea();
        createCopyDocumentLabel();
        createProgressLabel();
        createStartButton();
        setupDefaultDirectories();
        createAppFrame();
    }

    private void createAppFont() {
        appFont = new Font("Courier New", Font.BOLD, 18);
    }

    private void createCNoteLabel() {
        chooseCNoteLabel = new JLabel("Выберите накладную, опись или таблицу:");
        chooseCNoteLabel.setFont(appFont);
        chooseCNoteLabel.setBounds(50, 30, 500, 20);
    }

    private void createCNoteField() {
        chooseCNoteField = new JTextField();
        chooseCNoteField.setFont(appFont);
        chooseCNoteField.setDisabledTextColor(Color.BLACK);
        chooseCNoteField.setBounds(50, 50, 500, 30);
        chooseCNoteField.setEnabled(false);
    }

    private void createCNoteFileChooser() {
        cNoteFileChooser = new JFileChooser();
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
    }

    private void createChooseCNoteButton() {
        chooseCNoteButton = new JButton("Выбрать");
        chooseCNoteButton.setBounds(560, 50, 95, 30);
        chooseCNoteButton.addActionListener(event -> {
            int chooseResult = cNoteFileChooser.showOpenDialog(appFrame);
            if (chooseResult == JFileChooser.APPROVE_OPTION) {
                cNotePath = cNoteFileChooser.getSelectedFile().toPath();
                chooseCNoteField.setText(cNotePath.getFileName().toString());
            }
        });
    }

    private void createChooseDestinationLabel() {
        chooseDestinationLabel = new JLabel("Выберите папку, куда будут записаны копии:");
        chooseDestinationLabel.setFont(appFont);
        chooseDestinationLabel.setBounds(50, 100, 500, 20);
    }

    private void createChooseDestinationField() {
        chooseDestinationField = new JTextField();
        chooseDestinationField.setFont(appFont);
        chooseDestinationField.setDisabledTextColor(Color.BLACK);
        chooseDestinationField.setBounds(50, 120, 500, 30);
        chooseDestinationField.setEnabled(false);
    }

    private void createDestinationDirectoryChooser() {
        destinationDirectoryChooser = new JFileChooser();
        destinationDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    private void createChooseDestinationButton() {
        chooseDestinationButton = new JButton("Выбрать");
        chooseDestinationButton.setBounds(560, 120, 95, 30);
        chooseDestinationButton.addActionListener(event -> {
            int chooseResult = destinationDirectoryChooser.showOpenDialog(appFrame);
            if (chooseResult == JFileChooser.APPROVE_OPTION) {
                destinationDirectory = destinationDirectoryChooser.getSelectedFile().toPath();
                chooseDestinationField.setText(destinationDirectory.toString());
            }
        });
    }

    private void createCopyInfoLabel() {
        copyInfoLabel = new JLabel();
        copyInfoLabel.setFont(appFont);
        copyInfoLabel.setBounds(50, 190, 420, 20);
    }

    private void createCopyInfoArea() {
        copyInfoArea = new JTextArea(10, 20);
        copyInfoArea.setEnabled(false);
        copyInfoArea.setFont(appFont);
        copyInfoArea.setDisabledTextColor(Color.BLACK);
        copyInfoScroller = new JScrollPane(copyInfoArea);
        copyInfoScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        copyInfoScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        copyInfoScroller.setBounds(50, 210, 420, 300);
    }

    private void createErrorInfoLabel() {
        errorInfoLabel = new JLabel();
        errorInfoLabel.setFont(appFont);
        errorInfoLabel.setBounds(535, 190, 420, 20);
    }

    private void createErrorInfoArea() {
        errorInfoArea = new JTextArea(10, 20);
        errorInfoArea.setEnabled(false);
        errorInfoArea.setLineWrap(true);
        errorInfoArea.setFont(appFont);
        errorInfoArea.setDisabledTextColor(Color.RED);
        errorInfoScroller = new JScrollPane(errorInfoArea);
        errorInfoScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        errorInfoScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        errorInfoScroller.setBounds(535, 210, 420, 300);
    }

    private void createCopyDocumentLabel() {
        copyDocumentLabel = new JLabel();
        copyDocumentLabel.setFont(appFont);
        copyDocumentLabel.setBounds(360, 530, 500, 20);
    }


    private void createProgressLabel() {
        progressLabel = new JLabel();
        progressLabel.setFont(appFont);
        progressLabel.setBounds(360, 560, 400, 20);
    }

    private void createStartButton() {
        startButton = new JButton("Начать");
        startButton.setBounds(860, 610, 95, 40);
        startButton.addActionListener(event -> {
            String[] fileList = destinationDirectory.toFile().list();
            if (Objects.isNull(cNotePath)) {
                JOptionPane.showMessageDialog(appFrame, "Вы не выбрали накладную, опись или таблицу!", "Ошибка", ERROR_MESSAGE);
            } else if (Objects.nonNull(fileList) && fileList.length > 0) {
                setEmptyValuesToInfoAreas();
                JOptionPane.showMessageDialog(appFrame, "Папка для записи должна быть пуста!", "Ошибка", ERROR_MESSAGE);
            } else {
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
    }

    private void setupDefaultDirectories() {
        try {
            destinationDirectory = Directories.DEFAULT_DESTINATION_DIRECTORY;
            chooseDestinationField.setText(destinationDirectory.toString());
            cNoteFileChooser.setCurrentDirectory(new File(Directories.DEFAULT_C_NOTE_DIRECTORY.toString()));
            destinationDirectoryChooser.setCurrentDirectory(new File(Directories.DEFAULT_DESTINATION_DIRECTORY.toString()));
            DocSpecifiers.getDocSpecifier("СП"); //load properties on GUI setup phase to show exception if failed
        } catch (Exception e) {
            errorInfoArea.setText(e.getMessage());
        }
    }

    private void createAppFrame() {
        appFrame = new JFrame("Copy Maker");
        appFrame.setSize(1024, 750);
        appFrame.setLayout(null);
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    private void makeCopies() {
        log.info("Start create copies from consignment note:{}, destination directory:{}", cNotePath, destinationDirectory);
        SwingUtilities.invokeLater(() -> {
            startButton.setEnabled(false);
            setEmptyValuesToInfoAreas();
        });
        try {
            ConsignmentNoteReader noteReader = ConsignmentNoteReader.getReader(cNotePath.toString());
            Set<String> decimalNumbers = noteReader.getDecimalNumbers();
            if (decimalNumbers.isEmpty()) {
                JOptionPane.showMessageDialog(appFrame, "Выбранный перечень не содержит электронных документов!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            } else {
                DocumentPathMaker pathMaker = new DocumentPathMaker();
                DocumentCopyCreator copyCreator = new DocumentCopyCreator(destinationDirectory);
                AtomicInteger copyCounter = new AtomicInteger(0);
                SwingUtilities.invokeLater(() -> {
                    copyInfoLabel.setText("Ход копирования:");
                    progressLabel.setText(String.format("Скопировано документов: %d/%d", copyCounter.get(), decimalNumbers.size()));
                });
                decimalNumbers.forEach(decimalNumber -> {
                    try {
                        SwingUtilities.invokeLater(() -> copyDocumentLabel.setText("Копируется документ: " + decimalNumber));
                        Path documentPath = pathMaker.makePath(decimalNumber);
                        copyCreator.createCopy(documentPath);
                        String message = String.format("%-28sУспешно\n", decimalNumber);
                        SwingUtilities.invokeLater(() -> {
                            copyInfoArea.append(message);
                            progressLabel.setText(String.format("Скопировано документов: %d/%d", copyCounter.incrementAndGet(), decimalNumbers.size()));
                        });
                    } catch (Exception e) {
                        String message = String.format("%-28sНеудача\n", decimalNumber);
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
                log.info("Finish create copies from consignment note:{}, destination directory:{}", cNotePath, destinationDirectory);
            }
        } catch (UnsupportedConsignmentNoteException e) {
            JOptionPane.showMessageDialog(appFrame, "Выбранный перечень не поддерживается!", "Ошибка", ERROR_MESSAGE);
        } catch (Exception e) {
            String message = String.format("Failed create copies from consignment note:%s, destination directory:%s, " +
                    "cause:%s:%s", cNotePath, destinationDirectory, e.getClass().getName(), e.getMessage());
            log.error(message);
            SwingUtilities.invokeLater(() -> {
                errorInfoArea.setText(message);
                errorInfoArea.setCaretPosition(0);
            });
        }
        SwingUtilities.invokeLater(() -> {
            startButton.setEnabled(true);
        });
    }

    private void setEmptyValuesToInfoAreas() {
        copyInfoLabel.setText("");
        copyInfoArea.setText("");
        errorInfoArea.setText("");
        errorInfoLabel.setText("");
        copyDocumentLabel.setText("");
        progressLabel.setText("");
    }
}