package com.strong.web.controller;



import java.io.IOException;
import java.sql.SQLException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.strong.constant.ErrorCodes;
import com.strong.model.entity.User;
import com.strong.model.entity.UserToken;
import com.strong.exception.BusinessException;
import com.strong.exception.ErrorDetail;
import com.strong.exception.InputValidationException;
import com.strong.exception.ServiceException;
import com.strong.service.LoginServiceImpl;
import com.strong.service.RegisterService;
import com.strong.util.CheckPwdStrengthUtil;
import com.strong.web.model.RegisterRequest;
import com.strong.web.model.LoginResponse;

import com.strong.web.validator.RegisterRequestValidator;

@RestController
public class RegisterController {
	private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private LoginServiceImpl loginService;

	@Autowired
	private RegisterService registerService;

	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "*")
	//@ResponseBody
	public ResponseEntity<?> registAction(@RequestBody @Valid RegisterRequest req, BindingResult result)
			throws ServiceException, SQLException {
		if (result.hasErrors()) {
			throw new InputValidationException(result.getAllErrors());
		}

		logger.info("registAction -- new regist request for user - " + req.getUserName());

		User regUser = new User(req.getUserName(), req.getPassword(), req.getEmail(), req.getGender(),
				req.getSignature());

		User user = registerService.registNew(regUser);
		if (user == null) {
			throw new BusinessException(new ErrorDetail(ErrorCodes.FAIL, "Register is fail, please try again later."));
		}

		logger.info("loginAction -- new login request for user - " + req.getUserName());

		UserToken token = loginService.authentication(req.getUserName(), req.getPassword());
		if (token != null) {
    		return ResponseEntity
    				.status(HttpStatus.OK)
    				.body(new LoginResponse(user.getUsername(), token));       
		} else {
			ErrorDetail error = new ErrorDetail();
			error.setCode("LoginFail");
			error.setField(ErrorDetail.DEFAULT_CODE);
			error.setMessage("Login fail, please check id & password and retry");
			throw new BusinessException(error);
		}
	}

	@RequestMapping("/validPwd")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public int validPwdAction(@RequestParam("pass") String pass) throws IOException {

		return CheckPwdStrengthUtil.check(pass);// valid password strength,
												// return the strength code
	}
}
