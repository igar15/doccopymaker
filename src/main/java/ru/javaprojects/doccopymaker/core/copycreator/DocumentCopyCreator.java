package ru.javaprojects.doccopymaker.core.copycreator;

import ru.javaprojects.doccopymaker.core.properties.Directories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class DocumentCopyCreator {
    private final Path destinationDirectory;

    public DocumentCopyCreator(Path destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public void createCopy(Path documentDirectoryPath) {
        try {
            Stream<Path> pathStream = Files.list(Directories.ARCHIVE_DIRECTORY.resolve(documentDirectoryPath));
            if (!Files.exists(destinationDirectory.resolve(documentDirectoryPath))) {
                Files.createDirectories(destinationDirectory.resolve(documentDirectoryPath));
            }
            pathStream.forEach(path -> {
                try {
                    Path targetPath = destinationDirectory.resolve(documentDirectoryPath).resolve(path.getFileName());
                    Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new CopyCreationException("Failed to create copy of document:" + path + ", cause:" +
                            e.getClass().getName() + ":" + e.getMessage());
                }
            });
        } catch (IOException e) {
            throw new CopyCreationException("Failed to create copy of document:" + documentDirectoryPath + ", cause:" +
                    e.getClass().getName() + ":" + e.getMessage());
        }
    }
}