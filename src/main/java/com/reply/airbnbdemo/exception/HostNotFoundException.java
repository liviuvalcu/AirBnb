package com.reply.airbnbdemo.exception;

public class HostNotFoundException extends RuntimeException{
    public HostNotFoundException(String message){
        super(message);
    }
}
