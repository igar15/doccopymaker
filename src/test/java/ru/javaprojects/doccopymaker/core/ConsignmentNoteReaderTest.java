package ru.javaprojects.doccopymaker.core;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsignmentNoteReaderTest {

    @Test
    void getDecimalNumbersWhenBadFileType() throws IOException {
        ConsignmentNoteReader consignmentNoteReader = new ConsignmentNoteReader(Paths.get("C:/textFile.txt"));
        assertThrows(BadFileTypeException.class, consignmentNoteReader::getDecimalNumbers);
    }
}