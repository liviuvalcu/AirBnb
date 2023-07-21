package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.dao.JwtAuthenticationResponse;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.service.AuthenticationService;
import com.reply.airbnbdemo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration/")
@RequiredArgsConstructor
public class RegistrationController {


    private final RegistrationService registrationService;

    @PostMapping("register")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody RegistrationDao registrationDao){

        JwtAuthenticationResponse response = registrationService.registerUser(registrationDao);

        return ResponseEntity.ok(response);
    }
}
