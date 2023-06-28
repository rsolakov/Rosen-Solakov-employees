package com.sirma.emplyees.exception;

public class UnsupportedFileFormatException extends RuntimeException {

    public UnsupportedFileFormatException(String file) {
        super("Unsupported file format: %s".formatted(file));
    }
}
