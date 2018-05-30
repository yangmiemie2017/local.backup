package com.strong.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.strong.model.entity.User;
import com.strong.exception.ServiceException;

public interface RegisterService {
    
    public static final String BEAN_NAME = "registerService";
    
    public User registNew(User regUser) throws ServiceException, SQLException;

}
