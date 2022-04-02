package ru.javaprojects.doccopymaker.core;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class DocSpecifiers {
    private static final Properties docSpecifiers = new Properties();

    static {
        try (Reader reader = new InputStreamReader(DocSpecifiers.class.getClassLoader().getResourceAsStream("doc_specifiers.properties"),
                StandardCharsets.UTF_8)) {
            docSpecifiers.load(reader);
        } catch (Exception e) {
            throw new PropertiesLoadException("Failed to load doc_specifiers properties.");
        }
    }

    public static String getDocSpecifier(String docSpecifier) {
        return docSpecifiers.getProperty(docSpecifier);
    }
}