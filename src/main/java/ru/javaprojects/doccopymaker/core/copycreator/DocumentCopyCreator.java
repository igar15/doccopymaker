package ru.javaprojects.doccopymaker.core.copycreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javaprojects.doccopymaker.core.properties.Directories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class DocumentCopyCreator {
    private final Path destinationDirectory;
    private final Logger log = LoggerFactory.getLogger(getClass());

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
                    handleIoException(e, path);
                }
            });
        } catch (IOException e) {
            handleIoException(e, documentDirectoryPath);
        }
    }

    private void handleIoException(IOException e, Path docPath) {
        String message = String.format("Failed to create copy of document %s, cause:%s:%s",
                docPath, e.getClass().getName(), e.getMessage());
        log.error(message);
        throw new CopyCreationException(message);
    }
}