package com.reply.airbnbdemo.exception;

public class BookingAlreadyExist extends RuntimeException{
    public BookingAlreadyExist(String message){
        super(message);
    }
}
