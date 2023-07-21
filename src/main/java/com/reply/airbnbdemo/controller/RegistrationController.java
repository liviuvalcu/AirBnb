package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.dao.JwtAuthenticationResponse;
import com.reply.airbnbdemo.dao.RegistrationDao;
import com.reply.airbnbdemo.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody RegistrationDao registrationDao){
        JwtAuthenticationResponse response = authenticationService.signup(registrationDao.getSignUpRequest());

        //Insert user, user type si propietate

        return ResponseEntity.ok(response);
    }
}
