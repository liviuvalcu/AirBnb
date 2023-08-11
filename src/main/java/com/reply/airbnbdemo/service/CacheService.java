package com.reply.airbnbdemo.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @CacheEvict(cacheNames = {"getAllPropertiesByUserEmailOrCountry", "properties"}, allEntries = true)
    public void evictCache(){}

    @CacheEvict(value = "getAllPropertiesByUserEmailOrCountry", allEntries = true)
    public void evictCacheAllProperties(){}

    @CacheEvict(value = "properties", key = "#propertyName")
    public void evictCacheByPropertyName(String propertyName){}
}
