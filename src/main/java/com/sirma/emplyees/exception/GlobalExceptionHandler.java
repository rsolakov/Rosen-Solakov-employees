package com.sirma.emplyees.exception;

import com.sirma.emplyees.model.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling specific exceptions thrown within the application.
 * Provides custom error responses for specific exception types.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the NoCommonProjectsException and returns a custom error response with appropriate details.
     *
     * @param ex the NoCommonProjectsException that occurred.
     * @return a ResponseEntity containing the ErrorDetails with error code and message.
     */
    @ExceptionHandler(NoCommonProjectsException.class)
    public ResponseEntity<ErrorDetails> handleNoCommonProjectsException(NoCommonProjectsException ex) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .code(ErrorDetails.ErrorCode.COMMON_PROJECTS_NOT_FOUND)
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the UnsupportedFileFormatException and returns a custom error response with appropriate details.
     *
     * @param ex the UnsupportedFileFormatException that occurred.
     * @return a ResponseEntity containing the ErrorDetails with error code and message.
     */
    @ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<ErrorDetails> handleUnsupportedFileFormatException(UnsupportedFileFormatException ex) {
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .code(ErrorDetails.ErrorCode.UNSUPPORTED_FILE_FORMAT)
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
