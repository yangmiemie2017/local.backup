package com.strong.web.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



public class UpdateUserRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
//        return UpdateUserRequest.class.equals(clazz);
    	return true;
    }

    @Override
    public void validate(Object target, Errors errors) {     
//        UpdateUserRequest req = (UpdateUserRequest)target;
//        
//        if(StringUtils.isEmpty(req.getUser().getEmail())){
//        	errors.rejectValue("user.email", "EmailEmpty", "Email is empty.");
//        }
//        if(StringUtils.isEmpty(req.getUser().getStatus())){
//        	errors.rejectValue("user.status", "StatusEmpty", "Status is empty.");
//        }
//        boolean roleSelected = false; 
//        for(UserRoleModel role : req.getUser().getRoles()){
//        	if(role.getSelected()){
//        		roleSelected = true;
//        		break;
//        	}
//        }
//        if(!roleSelected){
//        	errors.rejectValue("user.roles", "RolesEmpty", "Please assign at least one role to the user.");
//        }
//        if(!StringUtils.isEmpty(req.getUser().getSignature()) && req.getUser().getSignature().length() > 100){
//        	errors.rejectValue("user.signature", "SignatureSizeExceed", "Signature only can have 100 characters.");
//        }
    }

}
