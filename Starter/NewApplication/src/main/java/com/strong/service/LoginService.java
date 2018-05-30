package com.strong.service;

import com.strong.model.entity.UserToken;

public interface LoginService {
    
    public static final String BEAN_NAME = "loginService";
    
    public UserToken authentication(String username, String password);
    
    public void logoff(String tokenId);
    
}
