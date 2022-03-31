package ru.javaprojects.doccopymaker.core;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ConsignmentNoteReader {
    private final Path cNotePath;
    private List<String> decimalNumbers;

    public ConsignmentNoteReader(Path cNotePath) {
        this.cNotePath = cNotePath;
    }

    public List<String> getDecimalNumbers() {
        if (Objects.isNull(decimalNumbers)) {
            decimalNumbers = readConsignmentNote();
        }
        return decimalNumbers;
    }

    private List<String> readConsignmentNote() {
        if (cNotePath.endsWith(".docx")) {
            return readConsignmentNoteFromDocxFileType();
        } else if (cNotePath.endsWith(".doc")) {
            return readConsignmentNoteFromDocFileType();
        } else {
            throw new BadFileTypeException("File type is not supported. Only \"doc\" and \"docx\" file types are supported.");
        }
    }

    private List<String> readConsignmentNoteFromDocxFileType() {
        return null;
    }

    private List<String> readConsignmentNoteFromDocFileType() {
        return null;
    }
}