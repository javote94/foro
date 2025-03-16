package com.javote.foro.exception;

public class ModeratorNotFoundException extends RuntimeException{
    public ModeratorNotFoundException(String message) {
        super(message);
    }
}
