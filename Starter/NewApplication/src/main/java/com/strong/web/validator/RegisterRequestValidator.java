package com.strong.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.strong.constant.ErrorCodes;


public class RegisterRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
//        return RegisterRequest.class.equals(clazz);
    	return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "userName", ErrorCodes.REGIST_USER_NAME_EMPTY, "User Name is empty.");
        ValidationUtils.rejectIfEmpty(errors, "password", ErrorCodes.REGIST_PASSWORD_EMPTY, "Password is empty.");
    }
}
