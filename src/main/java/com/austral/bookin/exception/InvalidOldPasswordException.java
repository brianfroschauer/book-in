package com.austral.bookin.exception;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException() {
        super("Old password doesn't match");
    }
}
