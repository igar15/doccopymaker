package ru.javaprojects.doccopymaker.core.pathmaker;

public abstract class PathMakingException extends RuntimeException {
    public PathMakingException(String message) {
        super(message);
    }
}