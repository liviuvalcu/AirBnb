package com.reply.airbnbdemo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class AirBnbExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getCause(),
                new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }
}