package ru.javaprojects.doccopymaker.core.reader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsignmentNoteWordReader extends ConsignmentNoteReader {
    private static final String DOC_SPLIT_CHARACTER = "\u0007";
    private static final String ELECTRONIC_DOCUMENT_IDENTIFIER = "ЭН";
    private String documentText;
    private boolean isDocFileType = false;

    ConsignmentNoteWordReader(String cNotePath) {
        try {
            if (cNotePath.endsWith(DOCX_FILE_EXTENSION)) {
                XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(cNotePath)));
                XWPFWordExtractor ex = new XWPFWordExtractor(document);
                this.documentText = ex.getText().toUpperCase();
            } else if (cNotePath.endsWith(DOC_FILE_EXTENSION)) {
                HWPFDocument document = new HWPFDocument(Files.newInputStream(Paths.get(cNotePath)));
                this.documentText = document.getDocumentText().toUpperCase();
                this.isDocFileType = true;
            }
        } catch (IOException e) {
            throw new FileReadingException(e.getMessage());
        }
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
    protected List<String> readDocsConsignmentNote() {
        List<String> decimalNumbers = new ArrayList<>();
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
    protected List<String> readChangeNoticesConsignmentNote() {
        List<String> decimalNumbers = new ArrayList<>();
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
    protected List<String> readRegularTableConsignmentNote() {
        String rowSplitCharacter = isDocFileType ? DOC_SPLIT_CHARACTER + DOC_SPLIT_CHARACTER : "\n";
        return Arrays.asList(documentText.trim().split(rowSplitCharacter));
    }
}