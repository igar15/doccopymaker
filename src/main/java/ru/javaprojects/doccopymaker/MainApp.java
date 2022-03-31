package ru.javaprojects.doccopymaker;


import org.apache.poi.hwpf.HWPFDocument;
import ru.javaprojects.doccopymaker.core.DocumentCopyCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainApp {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:/tests/doc.doc");
        HWPFDocument hwpfDocument = new HWPFDocument(Files.newInputStream(path));
        System.out.println();
        DocumentCopyCreator documentCopyCreator = new DocumentCopyCreator(path);
        System.out.println();
    }
}
