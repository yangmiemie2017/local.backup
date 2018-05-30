package com.strong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    
    public static final String CACHE_TOKEN = "TOKEN";
    
    
    @Autowired
    private CacheManager cacheManager;
    
    public void put(String cacheName, String key, Object value){
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(key, value);
    }
    
    public <T> T get(String cacheName, String key, Class<T> type){
        Cache cache = cacheManager.getCache(cacheName);
        return cache.get(key, type);
    }
    
    public void evict(String cacheName, String key){
        Cache cache = cacheManager.getCache(cacheName);
        cache.evict(key);
    }
}
