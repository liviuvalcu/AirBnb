package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration/")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping("create")
    public ResponseEntity<String> createListing(RegistrationDao registrationDao){
        registerService.createRegistrationDao(registrationDao);
        return  new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

}
