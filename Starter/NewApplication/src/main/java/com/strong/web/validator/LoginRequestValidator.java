package com.strong.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class LoginRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
//        return LoginRequest.class.equals(clazz);
    	return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "loginName", "LoginNameEmpty", "Login ID is empty.");
        ValidationUtils.rejectIfEmpty(errors, "password", "PasswordEmpty", "Password is empty.");
    }
}
