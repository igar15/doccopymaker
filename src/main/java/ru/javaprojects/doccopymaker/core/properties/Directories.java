package ru.javaprojects.doccopymaker.core.properties;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Directories {
    public static final Path ARCHIVE_DIRECTORY;
    public static final Path DEFAULT_C_NOTE_DIRECTORY;
    public static final Path DEFAULT_DESTINATION_DIRECTORY;

    static {
        try (Reader reader = new InputStreamReader(Directories.class.getClassLoader().getResourceAsStream("directories.properties"),
                StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(reader);
            ARCHIVE_DIRECTORY = Paths.get(properties.getProperty("archive_directory"));
            DEFAULT_C_NOTE_DIRECTORY = Paths.get(properties.getProperty("default_c_note_directory"));
            DEFAULT_DESTINATION_DIRECTORY = Paths.get(properties.getProperty("default_destination_directory"));
        } catch (Exception e) {
            throw new PropertiesLoadException("Failed to load directories properties.");
        }
    }
}