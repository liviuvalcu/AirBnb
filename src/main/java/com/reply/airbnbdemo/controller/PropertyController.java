package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.bean.PropertyBean;
import com.reply.airbnbdemo.dto.PropertyDto;
import com.reply.airbnbdemo.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/property/")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;


    @PostMapping("create")
    @CachePut(key = "#propertyBean.propertyName", cacheNames = {"properties", "getAllPropertiesByUserEmailOrCountry"})
    public ResponseEntity<String> createProperty(@RequestBody PropertyBean propertyBean){
        propertyService.createProperty(propertyBean);
        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("updatePrice")
    @CachePut("properties")
    public ResponseEntity<String> updatePropertyPrice(@RequestParam("propertyName") String propertyName,
                                                      @RequestParam("newPrice")BigDecimal newPrice){
        propertyService.updatePropertyPricePerNight(newPrice, propertyName);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("allByEmailOrCountry")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesByUserEmailOrCountry(@RequestParam(value = "userEmail", required = false) String userEmail,
                                                                                  @RequestParam(value = "country", required = false) String country){
        return ResponseEntity.ok( propertyService.getAllPropertiesByUserEmailOrCountry(userEmail, country));
    }

    @GetMapping("all")
    @Cacheable("getAllPropertiesByUserEmailOrCountry")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesByUserEmailOrCountry(){
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("byName")
    @Cacheable(value = "properties", key = "#propertyName")
    public ResponseEntity<PropertyDto> getByName(@RequestParam("propertyName") String propertyName){
        return ResponseEntity.ok(propertyService.getPropertyByNameDto(propertyName));
    }
}
