package com.javote.foro.exception;

public class UnauthorizedModeratorException extends RuntimeException {
    public UnauthorizedModeratorException(String message) {
        super(message);
    }
}
