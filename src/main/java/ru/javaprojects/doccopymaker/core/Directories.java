package ru.javaprojects.doccopymaker.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Directories {
    public static final Path ARCHIVE_DIRECTORY;
    public static final Path DEFAULT_C_NOTE_DIRECTORY;
    public static final Path DEFAULT_DESTINATION_DIRECTORY;

    static {
        try {
            InputStream inputStream = DocumentCopyCreator.class.getClassLoader().getResourceAsStream("directories.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            ARCHIVE_DIRECTORY = Paths.get(properties.getProperty("archive_directory"));
            DEFAULT_C_NOTE_DIRECTORY = Paths.get(properties.getProperty("default_c_note_directory"));
            DEFAULT_DESTINATION_DIRECTORY = Paths.get(properties.getProperty("default_destination_directory"));
        } catch (IOException e) {
            throw new PropertiesLoadException("Failed to load directories properties.");
        }
    }
}