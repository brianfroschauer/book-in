package com.austral.bookin.exception;

public class BadFormatException extends RuntimeException{
    public BadFormatException(){
        super("Entity has a bad format");
    }
}
