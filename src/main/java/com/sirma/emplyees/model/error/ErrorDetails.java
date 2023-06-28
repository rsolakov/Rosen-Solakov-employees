package com.sirma.emplyees.model.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ErrorDetails {

    private ErrorCode code;
    private String message;

    public enum ErrorCode {
        COMMON_PROJECTS_NOT_FOUND,
        UNSUPPORTED_FILE_FORMAT
    }
}