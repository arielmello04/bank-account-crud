package com.example.crud.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ExceptionDTO(
        String message,
        Integer status,
        String errorType,
        List<FieldError> fieldErrors
) {
    public record FieldError(String field, String message) {}

    public ExceptionDTO(String message, Integer status, String errorType) {
        this(message, status, errorType, null);
    }
}