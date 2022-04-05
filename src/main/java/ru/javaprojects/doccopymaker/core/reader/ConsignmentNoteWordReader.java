package ru.javaprojects.doccopymaker.core.reader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class ConsignmentNoteWordReader extends ConsignmentNoteReader {
    private static final String DOC_SPLIT_CHARACTER = "\u0007";
    private static final String ELECTRONIC_DOCUMENT_IDENTIFIER = "ЭН";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String documentText;
    private boolean isDocFileType = false;

    ConsignmentNoteWordReader(String cNotePath) {
        if (cNotePath.endsWith(DOCX_FILE_EXTENSION)) {
            try (InputStream fis = Files.newInputStream(Paths.get(cNotePath));
                 XWPFDocument document = new XWPFDocument(fis)) {
                XWPFWordExtractor ex = new XWPFWordExtractor(document);
                this.documentText = ex.getText().toUpperCase();
            } catch (IOException e) {
                handleIoException(e, cNotePath);
            }
        } else if (cNotePath.endsWith(DOC_FILE_EXTENSION)) {
            try (InputStream fis = Files.newInputStream(Paths.get(cNotePath));
                 HWPFDocument document = new HWPFDocument(fis)) {
                this.documentText = document.getDocumentText().toUpperCase();
                this.isDocFileType = true;
            } catch (IOException e) {
                handleIoException(e, cNotePath);
            }
        }
    }

    private void handleIoException(IOException e, String cNotePath) {
        String message = String.format("Failed to read file %s, cause:%s:%s", cNotePath, e.getClass().getName(), e.getMessage());
        log.error(message);
        throw new FileReadingException(message);
    }

    @Override
    protected boolean isFileDocsConsignmentNote() {
        return documentText.contains(DOCS_CONSIGNMENT_NOTE_IDENTIFIER);
    }

    @Override
    protected boolean isFileChangeNoticesConsignmentNote() {
        return documentText.contains(CHANGE_NOTICES_CONSIGNMENT_NOTE_IDENTIFIER);
    }

    @Override
    protected boolean isFileRegularTableConsignmentNote() {
        return documentText.contains(REGULAR_TABLE_CONSIGNMENT_NOTE_IDENTIFIER);
    }

    @Override
    protected Set<String> readDocsConsignmentNote() {
        Set<String> decimalNumbers = new TreeSet<>();
        String rowSplitCharacter = isDocFileType ? DOC_SPLIT_CHARACTER + DOC_SPLIT_CHARACTER : "\n";
        String[] tableRows = documentText.split(rowSplitCharacter);
        for (String row : tableRows) {
            String columnSplitCharacter = isDocFileType ? DOC_SPLIT_CHARACTER : "\t";
            String[] columns = row.split(columnSplitCharacter);
            for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                if (columns[colIndex].trim().equals(ELECTRONIC_DOCUMENT_IDENTIFIER)) {
                    decimalNumbers.add(columns[colIndex - 1].trim());
                }
            }
        }
        return decimalNumbers;
    }

    @Override
    protected Set<String> readChangeNoticesConsignmentNote() {
        Set<String> decimalNumbers = new TreeSet<>();
        String wordSplitCharacter = isDocFileType ? DOC_SPLIT_CHARACTER : "\t";
        String[] words = documentText.split(wordSplitCharacter);
        for (String word : words) {
            if (word.trim().endsWith("-" + ELECTRONIC_DOCUMENT_IDENTIFIER)) {
                decimalNumbers.add(word.replaceAll("-" + ELECTRONIC_DOCUMENT_IDENTIFIER + ".*", ""));
            }
        }
        return decimalNumbers;
    }

    @Override
    protected Set<String> readRegularTableConsignmentNote() {
        documentText = documentText.replace(REGULAR_TABLE_CONSIGNMENT_NOTE_IDENTIFIER, "");
        String rowSplitCharacter = isDocFileType ? DOC_SPLIT_CHARACTER + DOC_SPLIT_CHARACTER : "\n";
        return new TreeSet<>(Arrays.asList(documentText.trim().split(rowSplitCharacter)));
    }
}