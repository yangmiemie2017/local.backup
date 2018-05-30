package com.strong.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InputValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private List<ObjectError> errors;
    
    public InputValidationException(List<ObjectError> errors){
        this.errors = errors;
    }
    
    public List<ObjectError> getErrors(){
        return errors;
    }
}
