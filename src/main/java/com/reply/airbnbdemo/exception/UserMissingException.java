package com.reply.airbnbdemo.exception;

public class UserMissingException extends RuntimeException {
    public UserMissingException(String message){
        super(message);
    }
}
