package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
   To be used for different checks that returns messages to the client
 */

@RestController
@RequestMapping("/api/v1/messages/")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("propertyUpdated")
    public ResponseEntity<List<String>> checkIfPropertyUpdated(Authentication authentication){
        return ResponseEntity.ok(messageService.checkIfPropertyHasChanged(authentication.getName()));
    }
}
