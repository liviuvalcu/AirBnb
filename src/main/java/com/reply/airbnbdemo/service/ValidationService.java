package com.reply.airbnbdemo.service;

import com.reply.airbnbdemo.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationService {

    public void validateEmailOrCountry(String email, String country){
        if(Objects.isNull(email) && Objects.isNull(country))
            throw new ValidationException("Validation Exception Email or country should be filled");
    }
}
