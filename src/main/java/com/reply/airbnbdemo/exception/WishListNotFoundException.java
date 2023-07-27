package com.reply.airbnbdemo.exception;

public class WishListNotFoundException extends RuntimeException{
    public WishListNotFoundException(String message){
        super(message);
    }
}
