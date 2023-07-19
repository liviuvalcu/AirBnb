package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.PropertyDto;
import com.reply.airbnbdemo.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property/")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;


    @PostMapping("create")
    public ResponseEntity<String> createProperty(PropertyBean propertyBean){
        propertyService.createProperty(propertyBean);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @GetMapping("allByEmailOrCountry")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesByUserEmailOrCountry(@RequestParam(value = "userEmail", required = false) String userEmail,
                                                                                  @RequestParam(value = "country", required = false) String country){
        return ResponseEntity.ok( propertyService.getAllPropertiesByUserEmailOrCountry(userEmail, country));
    }

    @GetMapping("all")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesByUserEmailOrCountry(){
        return ResponseEntity.ok(propertyService.getAllProperties());
    }
}
