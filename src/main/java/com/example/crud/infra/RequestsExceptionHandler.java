package com.example.crud.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestControllerAdvice
public class RequestsExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ExceptionDTO.FieldError> errors = ex.getFieldErrors().stream()
                .map(e -> new ExceptionDTO.FieldError(e.getField(), e.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        "Validation failed",
                        400,
                        "VALIDATION_ERROR",
                        errors
                )
        );
    }

    // Handle duplicate entries
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> handleDuplicateEntry(DataIntegrityViolationException ex) {
        String message = "Data conflict";
        if (ex.getMessage().contains("constraint [account_number_agency_unique]")) {
            message = "Account number already exists for this agency";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionDTO(message, 409, "DATA_INTEGRITY_VIOLATION")
        );
    }

    // Handle not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleEntityNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDTO("Resource not found", 404, "NOT_FOUND")
        );
    }

    // Generic exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleGenericExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDTO("Internal server error", 500, "INTERNAL_ERROR")
        );
    }
}