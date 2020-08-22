package com.austral.bookin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handle(NotFoundException exception) {
        return handle(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handle(AlreadyExistsException exception) {
        return handle(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiError> handle(InternalServerException exception) {
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiError> handle(IOException exception) {
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ApiError> handle(HttpStatus status, String message) {
        return new ResponseEntity<>(new ApiError(status, message), status);
    }
}