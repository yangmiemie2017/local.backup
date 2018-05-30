package com.strong.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.strong.exception.BusinessException;
import com.strong.exception.InputValidationException;
import com.strong.exception.ServiceException;


@ControllerAdvice
@Order(100)
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInputValidationException(InputValidationException e){
        if(logger.isDebugEnabled()){
            logger.debug("InputValidationException occurred - " + e.getMessage());
        }
        
        String res="InputValidationException occurred - " + e.getMessage();
        return res;
    }
    
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleBusinessException(BusinessException e){
        if(logger.isDebugEnabled()){
            logger.debug("BusinessException occurred - " + e.getMessage());
        }
        
        String res="BusinessException occurred - " + e.getMessage();
        return res;
    }
    
    
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleServiceException(ServiceException e){
        if(logger.isDebugEnabled()){
            logger.debug("ServiceException occurred - " + e.getMessage());
        }
        
        String res="ServiceException occurred - " + e.getMessage();
        return res;
    }
}
