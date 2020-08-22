package com.austral.bookin.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException() {
        super("Entity already exists");
    }
}