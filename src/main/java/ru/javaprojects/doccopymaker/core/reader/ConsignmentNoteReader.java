package ru.javaprojects.doccopymaker.core.reader;

import java.util.List;
import java.util.Objects;

public abstract class ConsignmentNoteReader {
    public static final String DOC_FILE_EXTENSION = ".doc";
    public static final String DOCX_FILE_EXTENSION = ".docx";
    public static final String DOCS_CONSIGNMENT_NOTE_IDENTIFIER = "НАКЛАДНАЯ";
    public static final String CHANGE_NOTICES_CONSIGNMENT_NOTE_IDENTIFIER = "ОПИСЬ";
    private List<String> decimalNumbers;

    public static ConsignmentNoteReader getReader(String cNotePath) {
        if (cNotePath.endsWith(DOCX_FILE_EXTENSION) || cNotePath.endsWith(DOC_FILE_EXTENSION)) {
            return new ConsignmentNoteWordReader(cNotePath);
        } else {
            throw new BadFileTypeException(String.format("File type is not supported. Only \"%s\" and \"%s\" file types " +
                            "are supported.", DOC_FILE_EXTENSION, DOCX_FILE_EXTENSION));
        }
    }

    public List<String> getDecimalNumbers() {
        if (Objects.isNull(decimalNumbers)) {
            decimalNumbers = readConsignmentNote();
        }
        return decimalNumbers;
    }

    private List<String> readConsignmentNote() {
        if (isFileDocsConsignmentNote()) {
            return readDocsConsignmentNote();
        } else if (isFileChangeNoticesConsignmentNote()) {
            return readChangeNoticesConsignmentNote();
        } else {
            return readRegularTableConsignmentNote();
        }
    }

    protected abstract boolean isFileDocsConsignmentNote();

    protected abstract boolean isFileChangeNoticesConsignmentNote();

    protected abstract List<String> readDocsConsignmentNote();

    protected abstract List<String> readChangeNoticesConsignmentNote();

    protected abstract List<String> readRegularTableConsignmentNote();
}