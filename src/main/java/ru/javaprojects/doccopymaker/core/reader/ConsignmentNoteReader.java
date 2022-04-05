package ru.javaprojects.doccopymaker.core.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

public abstract class ConsignmentNoteReader {
    private static final Logger log = LoggerFactory.getLogger(ConsignmentNoteReader.class);
    public static final String DOC_FILE_EXTENSION = ".doc";
    public static final String DOCX_FILE_EXTENSION = ".docx";
    public static final String DOCS_CONSIGNMENT_NOTE_IDENTIFIER = "НАКЛАДНАЯ";
    public static final String CHANGE_NOTICES_CONSIGNMENT_NOTE_IDENTIFIER = "ОПИСЬ";
    public static final String REGULAR_TABLE_CONSIGNMENT_NOTE_IDENTIFIER = "СПИСОК ЭЛЕКТРОННЫХ ДОКУМЕНТОВ";
    private Set<String> decimalNumbers;

    public static ConsignmentNoteReader getReader(String cNotePath) {
        if (cNotePath.endsWith(DOCX_FILE_EXTENSION) || cNotePath.endsWith(DOC_FILE_EXTENSION)) {
            return new ConsignmentNoteWordReader(cNotePath);
        } else {
            String message = String.format("File type is not supported. Only \"%s\" and \"%s\" file types " +
                    "are supported.", DOC_FILE_EXTENSION, DOCX_FILE_EXTENSION);
            log.error(message);
            throw new UnsupportedFileTypeException(message);
        }
    }

    public Set<String> getDecimalNumbers() {
        if (Objects.isNull(decimalNumbers)) {
            decimalNumbers = readConsignmentNote();
        }
        return decimalNumbers;
    }

    private Set<String> readConsignmentNote() {
        if (isFileDocsConsignmentNote()) {
            return readDocsConsignmentNote();
        } else if (isFileChangeNoticesConsignmentNote()) {
            return readChangeNoticesConsignmentNote();
        } else if (isFileRegularTableConsignmentNote()) {
            return readRegularTableConsignmentNote();
        } else {
            throw new UnsupportedConsignmentNoteException("This consignment note type is not supported");
        }
    }

    protected abstract boolean isFileDocsConsignmentNote();

    protected abstract boolean isFileChangeNoticesConsignmentNote();

    protected abstract boolean isFileRegularTableConsignmentNote();

    protected abstract Set<String> readDocsConsignmentNote();

    protected abstract Set<String> readChangeNoticesConsignmentNote();

    protected abstract Set<String> readRegularTableConsignmentNote();
}