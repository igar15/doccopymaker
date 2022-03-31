package ru.javaprojects.doccopymaker.core;

import java.nio.file.Path;

public class DocumentCopyCreator {
    private final Path destinationDirectory;
    private final DocumentPathMaker documentPathMaker;

    public DocumentCopyCreator(Path destinationDirectory, DocumentPathMaker documentPathMaker) {
        this.destinationDirectory = destinationDirectory;
        this.documentPathMaker = documentPathMaker;
    }

    public void createCopy(String decimalNumber) {

    }
}