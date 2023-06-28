package com.sirma.emplyees.exception;

public class NoCommonProjectsException extends RuntimeException {

    public NoCommonProjectsException() {
        super("There is no data in the file about employees who have worked together on the same project at the same time.");
    }
}
