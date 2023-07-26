package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.bean.BookingBean;
import com.reply.airbnbdemo.dto.BookingDto;
import com.reply.airbnbdemo.enums.PropertyEnum;
import com.reply.airbnbdemo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking/")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("create")
    public ResponseEntity<String> createBooking(@RequestBody BookingBean bookingBean){
        bookingService.createBooking(bookingBean);
        return  new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @GetMapping("byPropertyName")
    public ResponseEntity<List<BookingDto>> getAllByPropertyName(@RequestParam("propertyName") String propertyName){
        return ResponseEntity.ok(bookingService.getAllByPropertyName(propertyName));
    }
}
