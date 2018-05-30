package com.strong.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<ErrorDetail> errors;

    public ServiceException(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public ServiceException(ErrorDetail error) {
        errors = new ArrayList<ErrorDetail>();
        errors.add(error);
    }

    public List<ErrorDetail> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public ServiceException(String code, String message) {
        errors = new ArrayList<ErrorDetail>();
        errors.add(new ErrorDetail(code, message));
    }
}
