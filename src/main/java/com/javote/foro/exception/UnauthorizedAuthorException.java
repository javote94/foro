package com.javote.foro.exception;

public class UnauthorizedAuthorException extends RuntimeException{
    public UnauthorizedAuthorException(String message) {
        super(message);
    }
}
