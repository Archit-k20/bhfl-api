package com.bfhl.exception;

import com.bfhl.config.AppProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AppProperties appProperties;

    public GlobalExceptionHandler(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    static class ErrorPayload {
        @JsonProperty("is_success")
        public boolean isSuccess = false;

        @JsonProperty("user_id")
        public String userId;

        @JsonProperty("message")
        public String message;

        public ErrorPayload(String userId, String message) {
            this.userId = userId;
            this.message = message;
        }
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<ErrorPayload> handleInvalidRequest(InvalidRequestException ex) {
        ErrorPayload payload = new ErrorPayload(appProperties.buildUserId(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorPayload> handleValidation(Exception ex) {
        ErrorPayload payload = new ErrorPayload(appProperties.buildUserId(), "Invalid request payload");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> handleGeneric(Exception ex) {
        ErrorPayload payload = new ErrorPayload(appProperties.buildUserId(), "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(payload);
    }
}