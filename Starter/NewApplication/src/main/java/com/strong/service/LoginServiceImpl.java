package com.strong.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.strong.service.*;
import com.strong.dataaccess.DLUser;
import com.strong.model.entity.User;
import com.strong.model.entity.UserToken;

//import com.strong.util.PwdEncoder;


@Service(LoginService.BEAN_NAME)
public class LoginServiceImpl implements LoginService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final long TOKEN_EXPIRE_INTERVAL = 5 * 60 * 1000;//30 minutes  
    
    @Autowired
    private CacheService cacheService;
    
    @Override
    public UserToken authentication(String username, String password){
        
        if(StringUtils.isEmpty(username)){
            return null;
        }
        if(StringUtils.isEmpty(password)){
            return null;
        }       
        
        User user = null;
		try {
			user = DLUser.getUser(username);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if(user==null){
        	logger.error("user ["+ username + "] don't exit!");
        	return null;
        }    		
        
		try {
			user = DLUser.getUser(username,password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        if(user==null){
        	logger.error("user ["+ username + "] password mismatch!");			
        	return null;
        }    		
           
        
//        //encrypt password and compare
//        String comparedPassword = "";//input pwd to be compare
//        
//		try {
//			user = DLUser.getUser(username);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}        
//        //userName are correct, so have same first param
//        try {
//        	logger.info("db password:"+user.getPassword());
//			//comparedPassword = PwdEncoder.encryptSHA2MAC(username, password);
//        	comparedPassword=password;
//			logger.info("comparedPassword:"+comparedPassword);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        
//        if(!comparedPassword.equals(user.getPassword())){
//            logger.error("user ["+ user.getUsername() + "] authentication fail!");
//            return null;
//        }
        
        if("N".equals(user.getStatus())){
            logger.error("Status ["+ user.getStatus() + "] authentication fail! user not Active");
            return null;
        }
        
        logger.info("user ["+ user.getUsername() + "] authentication success!");
        logger.info(user.toString());
        
        UserToken token = new UserToken(user, TOKEN_EXPIRE_INTERVAL);
        cacheService.put(CacheService.CACHE_TOKEN, token.getId(), token);
        return token;
    }
    
    @Override
    public void logoff(String tokenId){
        if(!StringUtils.isEmpty(tokenId)){
            cacheService.evict(CacheService.CACHE_TOKEN, tokenId);
            logger.info("user ["+ tokenId + "] logoff success!");            
        }
    }
}
