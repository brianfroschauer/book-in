package com.austral.bookin.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Entity not found");
    }
}
