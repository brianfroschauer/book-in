package com.austral.bookin.exception;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
        super("This token has expired");
    }
}
