package ru.javaprojects.doccopymaker.core.copycreator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentCopyCreatorTest {

    @Test
    void createCopy() throws IOException {
        Path tempDirectory = Files.createTempDirectory("tempDir");
        DocumentCopyCreator creator = new DocumentCopyCreator(tempDirectory);
        Path documentPath = Paths.get("VUIA/436634/009/SP");
        creator.createCopy(documentPath);
        List<Path> fileList = Files.list(tempDirectory.resolve(documentPath)).collect(Collectors.toList());
        assertEquals("1.txt", fileList.get(0).getFileName().toString());
        assertEquals("2.txt", fileList.get(1).getFileName().toString());
        assertEquals("3.txt", fileList.get(2).getFileName().toString());
    }

    @Test
    void createSoftwareCopy() throws IOException {
        Path tempDirectory = Files.createTempDirectory("tempDir");
        DocumentCopyCreator creator = new DocumentCopyCreator(tempDirectory);
        Path documentPath = Paths.get("VUIA/20196/-01/12/01");
        creator.createCopy(documentPath);
        List<Path> fileList = Files.list(tempDirectory.resolve(documentPath)).collect(Collectors.toList());
        assertEquals("1.txt", fileList.get(0).getFileName().toString());
        assertEquals("2.txt", fileList.get(1).getFileName().toString());
    }

    @Test
    void createCopyAlreadyExist() throws IOException {
        Path tempDirectory = Files.createTempDirectory("tempDir");
        DocumentCopyCreator creator = new DocumentCopyCreator(tempDirectory);
        Path documentPath = Paths.get("VUIA/436634/009/SP");
        creator.createCopy(documentPath);
        creator.createCopy(documentPath);
        List<Path> fileList = Files.list(tempDirectory.resolve(documentPath)).collect(Collectors.toList());
        assertEquals("1.txt", fileList.get(0).getFileName().toString());
        assertEquals("2.txt", fileList.get(1).getFileName().toString());
        assertEquals("3.txt", fileList.get(2).getFileName().toString());
    }

    @Test
    void createCopyWhenDocumentNotFound() throws IOException {
        Path tempDirectory = Files.createTempDirectory("tempDir");
        DocumentCopyCreator creator = new DocumentCopyCreator(tempDirectory);
        Path documentPath = Paths.get("VUIA/455666/001/SP");
        assertThrows(CopyCreationException.class, () -> creator.createCopy(documentPath));
    }
}