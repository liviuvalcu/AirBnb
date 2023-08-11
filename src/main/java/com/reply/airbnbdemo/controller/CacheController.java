package com.reply.airbnbdemo.controller;

import com.reply.airbnbdemo.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache/")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("evict")
    public ResponseEntity<String> evictCache(){
            cacheService.evictCache();
        return ResponseEntity.ok().build();
    }

    @GetMapping("evict/allProperties")
    public ResponseEntity<String> evictAllProperties(){
        cacheService.evictCacheAllProperties();
        return ResponseEntity.ok().build();
    }

    @GetMapping("evict/propertyName")
    public ResponseEntity<String> evictProperty(@RequestParam("propertyName") String propertyName){
        cacheService.evictCacheByPropertyName(propertyName);
        return ResponseEntity.ok().build();
    }


}
