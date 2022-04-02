package ru.javaprojects.doccopymaker.core.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javaprojects.doccopymaker.TestData.*;

class ConsignmentNoteReaderTest {

    @Test
    void getReaderWhenBadFileType() {
        assertThrows(BadFileTypeException.class, () -> ConsignmentNoteReader.getReader("C:/textFile.txt"));
    }

    @Test
    void getDecimalNumbersFromDocsConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/docs_consignment_note.doc");
        assertEquals(docsConsignmentNoteDecimalNumbers, reader.getDecimalNumbers());
    }

    @Test
    void getDecimalNumbersFromChangeNoticesConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/change_notices_consignment_note.doc");
        assertEquals(changeNoticesConsignmentNoteDecimalNumbers, reader.getDecimalNumbers());
    }

    @Test
    void getDecimalNumbersFromRegularTableConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/regular_table_consignment_note.doc");
        assertEquals(regularTableDecimalNumbers, reader.getDecimalNumbers());
    }

    @Test
    void getDecimalNumbersFromDocsConsignmentNoteDocxFleType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/docs_consignment_note.docx");
        assertEquals(docsConsignmentNoteDecimalNumbers, reader.getDecimalNumbers());
    }

    @Test
    void getDecimalNumbersFromChangeNoticesConsignmentNoteDocxFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/change_notices_consignment_note.docx");
        assertEquals(changeNoticesConsignmentNoteDecimalNumbers, reader.getDecimalNumbers());
    }

    @Test
    void getDecimalNumbersFromRegularTableConsignmentNoteDocxFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("src/test/resources/regular_table_consignment_note.docx");
        assertEquals(regularTableDecimalNumbers, reader.getDecimalNumbers());
    }
}