package com.strong.web.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.strong.constant.ErrorCodes;
import com.strong.model.dto.PaginatedResult;
import com.strong.model.entity.User;
import com.strong.model.entity.UserToken;
import com.strong.exception.BusinessException;
import com.strong.exception.ErrorDetail;
import com.strong.exception.InputValidationException;
import com.strong.exception.ServiceException;
import com.strong.service.LoginServiceImpl;
import com.strong.service.RegisterService;
import com.strong.util.CheckPwdStrengthUtil;
import com.strong.util.*;
import com.strong.web.model.RegisterRequest;
import com.strong.web.model.LoginResponse;
import com.strong.common.Data;

@RestController
public class ApiServiceController {
	private static Logger logger = LoggerFactory.getLogger(ApiServiceController.class);

	@RequestMapping(value = "/api", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> post(@RequestBody JsonContainer container)
			throws ServiceException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		logger.info("api/ - " + container.toString());
		Object obj = container.getObject();
		if (obj instanceof Data)
			obj = ((Data) obj).data;
		return ResponseEntity.ok(obj);

	}
}
