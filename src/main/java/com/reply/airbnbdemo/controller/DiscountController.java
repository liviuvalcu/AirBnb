package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.dto.DiscountDto;
import com.reply.airbnbdemo.enums.DiscountLevels;
import com.reply.airbnbdemo.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parameters/")
@RequiredArgsConstructor
public class DiscountController {


    private final DiscountService discountService;

    @PostMapping("create")
    public ResponseEntity<String> createDiscount(@RequestParam("name") String name,
                                                 @RequestParam("minimNights") Integer minimNights,
                                                 @RequestParam("minimalAmountSpent") BigDecimal minimalAmountSpent,
                                                 @RequestParam("discountLevels") DiscountLevels discountLevels){
        discountService.createDiscount(name, minimNights, minimalAmountSpent, discountLevels);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateDiscount(@RequestParam("name") String name,
                                                 @RequestParam("minimNights") Integer minimNights,
                                                 @RequestParam("minimalAmountSpent") BigDecimal minimalAmountSpent,
                                                 @RequestParam("discountLevels") DiscountLevels discountLevels){
        discountService.updateDiscount(name, minimNights, minimalAmountSpent, discountLevels);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteDiscount(@RequestParam("discountLevels") DiscountLevels discountLevels){
        discountService.deleteDiscount(discountLevels);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<DiscountDto>> getAll(){
        return ResponseEntity.ok(discountService.getAll());
    }
}
