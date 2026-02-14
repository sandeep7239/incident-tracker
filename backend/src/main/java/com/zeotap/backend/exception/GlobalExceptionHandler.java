package com.zeotap.backend.exception;

import com.zeotap.backend.constant.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(CommonConstants.ERROR_RESPONSE_KEY_TIMESTAMP, Instant.now().toString());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_STATUS, HttpStatus.NOT_FOUND.value());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_ERROR, CommonConstants.ERROR_NOT_FOUND);
        body.put(CommonConstants.ERROR_RESPONSE_KEY_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
            errors.put(field, error.getDefaultMessage());
        });
        Map<String, Object> body = new HashMap<>();
        body.put(CommonConstants.ERROR_RESPONSE_KEY_TIMESTAMP, Instant.now().toString());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_ERROR, CommonConstants.ERROR_VALIDATION_FAILED);
        body.put(CommonConstants.ERROR_RESPONSE_KEY_ERRORS, errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put(CommonConstants.ERROR_RESPONSE_KEY_TIMESTAMP, Instant.now().toString());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_STATUS, HttpStatus.UNAUTHORIZED.value());
        body.put(CommonConstants.ERROR_RESPONSE_KEY_ERROR, "Unauthorized");
        body.put(CommonConstants.ERROR_RESPONSE_KEY_MESSAGE, "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
