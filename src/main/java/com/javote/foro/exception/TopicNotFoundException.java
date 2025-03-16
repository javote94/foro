package com.javote.foro.exception;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String message) {
        super(message);
    }
}
