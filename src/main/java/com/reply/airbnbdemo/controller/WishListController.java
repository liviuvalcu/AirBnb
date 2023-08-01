package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.dto.WishlistDto;
import com.reply.airbnbdemo.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishList/")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("create")
    public ResponseEntity<String> createWishList(@RequestParam("propertiesToBeIncluded") List<String> propertiesToBeIncluded,
                                                 @RequestParam("wishListName") String wishListName,
                                                 @RequestParam("privacy") String privacy,
                                                 Authentication authentication){

                wishListService.createWishList(authentication.getName(), wishListName, privacy);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("properties")
    public ResponseEntity<String> addProperties(@RequestParam("propertiesToBeIncluded") List<String> propertiesToBeIncluded,
                                                @RequestParam("wishListName") String wishListName,
                                                Authentication authentication){

        wishListService.addPropertiesToWishList(wishListName, propertiesToBeIncluded, authentication.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("update")
    public ResponseEntity<String> updateWishList(@RequestParam("wishListName")String wishListName,
                                                 @RequestParam("propertiesToBeIncluded") List<String> propertiesToBeIncluded,
                                                 @RequestParam("privacy") String privacy,
                                                 Authentication authentication){
        wishListService.updateWishList(authentication.getName(), wishListName, privacy, propertiesToBeIncluded);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("addProperties")
    public ResponseEntity<String> addPropertiesWishList(@RequestParam("wishListName")String wishListName,
                                                        @RequestParam("propertiesToBeIncluded") List<String> propertiesToBeIncluded,
                                                 Authentication authentication){
        wishListService.addNewProperties(authentication.getName(), wishListName, propertiesToBeIncluded);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteWishList(@RequestParam("wishListName")String wishListName, Authentication authentication){
        wishListService.deleteWishList(wishListName, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<List<WishlistDto>> getAllWishLists(Authentication authentication){
        return ResponseEntity.ok(wishListService.getAll(authentication.getName()));
    }

}
