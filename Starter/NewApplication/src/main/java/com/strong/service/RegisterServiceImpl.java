package com.strong.service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.strong.constant.ErrorCodes;
import com.strong.dataaccess.DLUser;
//import com.strong.dao.AuthAdminDao;
//import com.strong.dao.AuthorityDao;
//import com.strong.dao.UserDao;
//import com.strong.dao.UserRoleDao;
//import com.strong.entity.AuthAdmin;
import com.strong.model.entity.Authority;
import com.strong.model.entity.User;
import com.strong.model.entity.UserAuthority;

//import com.strong.entity.UserRole;
import com.strong.exception.ServiceException;
import com.strong.util.CheckPwdStrengthUtil;
//import com.strong.util.PwdEncoder;

@Service(RegisterService.BEAN_NAME)
public class RegisterServiceImpl implements RegisterService {
	private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	@Transactional(readOnly = false)
    public User registNew(User regUser) throws ServiceException, SQLException {
		String userName = regUser.getUsername();
		String password = regUser.getPassword();
		
        Assert.notNull(userName, "User Name cannot be null");
        Assert.notNull(password, "password cannot be null");

        logger.info("new regist for user - " + userName);

        User existingUser = DLUser.getUser(userName);
        if (existingUser!=null) {
            throw new ServiceException(ErrorCodes.REGIST_USER_NAME_EXIST, "User Name is existed.");
        }

//        int passwordLevel = CheckPwdStrengthUtil.check(password);
//        if (passwordLevel < 2) {
//            throw new ServiceException(ErrorCodes.REGIST_PASSWORD_WEAK, "Password is too weak.");
//        }

        String passwordEncryption = null;
//        try {
//            passwordEncryption = PwdEncoder.encryptSHA2MAC(userName, password);
//        } catch (Exception e) {
//            logger.error("Exception occurred when encrypt the password - " + e.getMessage());
//            if (logger.isDebugEnabled()) {
//                e.printStackTrace();
//            }
//            return null;
//        }

        passwordEncryption=password;
        User user = new User();
        user.setUsername(userName);
        user.setPassword(passwordEncryption);
        user.setStatus("A");
        user.setRole("U");
        user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        user.setGender(regUser.getGender());
        user.setEmail(regUser.getEmail());
        user.setSignature(regUser.getSignature());
        user.setAuthorities(new HashSet<UserAuthority>());

//        UserAuthority defaultRole = authorityDao.findByAuthority(Authority.USER.getCode());
//        if (defaultRole != null) {
//            user.getAuthorities().add(defaultRole);
//        } else {
//            logger.warn("Not able to find default Authority, set it to blank for user - " + userName);
//        }
        DLUser.insertUser(user);
        return user;
    }
}
