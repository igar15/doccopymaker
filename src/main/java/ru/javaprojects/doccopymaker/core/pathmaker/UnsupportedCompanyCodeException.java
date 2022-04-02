package ru.javaprojects.doccopymaker.core.pathmaker;

public class UnsupportedCompanyCodeException extends PathMakingException {
    public UnsupportedCompanyCodeException(String message) {
        super(message);
    }
}