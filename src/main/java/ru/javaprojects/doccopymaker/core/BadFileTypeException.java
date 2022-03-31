package ru.javaprojects.doccopymaker.core;

public class BadFileTypeException extends RuntimeException {
    public BadFileTypeException(String message) {
        super(message);
    }
}