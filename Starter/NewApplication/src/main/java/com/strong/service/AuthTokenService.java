package com.strong.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.strong.model.entity.UserToken;

@Service
public class AuthTokenService {
    private static Logger logger = LoggerFactory.getLogger(AuthTokenService.class);
    
    @Autowired
    private CacheService cacheService;
    
    //TODO: customize the exception later
    public com.strong.model.entity.UserToken loadTokenById(String id) throws UsernameNotFoundException {
        logger.info("calling loadTokenById for id - "+ id);
        
        UserToken token = cacheService.get(CacheService.CACHE_TOKEN, id, UserToken.class);
        
        if(token == null){
            throw new UsernameNotFoundException("Cannot find the requested token - " + id);
        } else if(!token.isValid()){
            cacheService.evict(CacheService.CACHE_TOKEN, id);
            throw new UsernameNotFoundException("Token is already Expired - " + id);
        }
        
        token.refresh();
        cacheService.put(CacheService.CACHE_TOKEN, token.getId(), token);
        
        return token;
    }
}
