package com.javote.foro.exception;

public class ResponseNotFoundException extends RuntimeException{
    public ResponseNotFoundException(String message) {
        super(message);
    }
}
