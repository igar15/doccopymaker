package ru.javaprojects.doccopymaker;


import ru.javaprojects.doccopymaker.core.reader.ConsignmentNoteReader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws IOException {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("E:/ARCHIV_22_N/doc/Описи/3_sheets.doc");
        List<String> decimalNumbers = reader.getDecimalNumbers();
        System.out.println(decimalNumbers);
    }

    public static void readDocsConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("E:/ARCHIV_22_N/doc/Накладные/3 sheets with ed.doc");
        List<String> decimalNumbers = reader.getDecimalNumbers();
        System.out.println(decimalNumbers);
    }

    public static void readChangeNoticesConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("E:/ARCHIV_22_N/doc/Описи/3_sheets.doc");
        List<String> decimalNumbers = reader.getDecimalNumbers();
        System.out.println(decimalNumbers);
    }

    public static void readRegularTableConsignmentNoteDocFileType() {
        ConsignmentNoteReader reader = ConsignmentNoteReader.getReader("E:/ARCHIV_22_N/doc/Обычные таблицы/все эн.doc");
        List<String> decimalNumbers = reader.getDecimalNumbers();
        System.out.println(decimalNumbers);
    }
}
