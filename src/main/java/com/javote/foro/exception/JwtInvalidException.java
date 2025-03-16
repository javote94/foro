package com.javote.foro.exception;

public class JwtInvalidException extends RuntimeException{
    public JwtInvalidException(String message) {
        super(message);
    }
}
