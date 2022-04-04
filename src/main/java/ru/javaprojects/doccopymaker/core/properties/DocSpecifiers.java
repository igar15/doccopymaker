package ru.javaprojects.doccopymaker.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class DocSpecifiers {
    private static final Logger log = LoggerFactory.getLogger(DocSpecifiers.class);
    private static final Properties docSpecifiers = new Properties();

    static {
        try (Reader reader = new InputStreamReader(DocSpecifiers.class.getClassLoader().getResourceAsStream("doc_specifiers.properties"),
                StandardCharsets.UTF_8)) {
            docSpecifiers.load(reader);
        } catch (Exception e) {
            String message = String.format("Failed to load doc_specifiers properties, cause:%s:%s", e.getClass().getName(), e.getMessage());
            log.error(message);
            throw new PropertiesLoadException(message);
        }
    }

    public static String getDocSpecifier(String docSpecifier) {
        return docSpecifiers.getProperty(docSpecifier);
    }
}