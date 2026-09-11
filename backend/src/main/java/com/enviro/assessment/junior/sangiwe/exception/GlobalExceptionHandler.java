package com.enviro.assessment.junior.sangiwe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice means: intercept exceptions thrown from ANY @RestController
// in the app, in one central place, instead of try/catch in every controller method.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catches all three of our custom rule-violation exceptions at once,
    // since they all extend the shared WithdrawalException base class.
    @ExceptionHandler(WithdrawalException.class)
    public ResponseEntity<Map<String, Object>> handleWithdrawalException(WithdrawalException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Catches the "not found" cases from findById().orElseThrow(...)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Catches @Valid failures from the DTO validation annotations (@NotNull, @Positive)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");

        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    // Catch-all safety net for anything unexpected — so a client NEVER sees
    // a raw Java stack trace, even if something goes wrong that we didn't anticipate.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}