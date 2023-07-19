package com.reply.airbnbdemo.controller.auth;

import com.reply.airbnbdemo.dao.JwtAuthenticationResponse;
import com.reply.airbnbdemo.dao.SignUpRequest;
import com.reply.airbnbdemo.dao.SigninRequest;
import com.reply.airbnbdemo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class JwtAuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest signUpRequest) throws Exception {

       return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }


}
