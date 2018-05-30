package com.strong.util;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;

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
import com.strong.web.controller.ApiServiceController;
import com.strong.web.model.LoginResponse;

@Service
public class JsonContainer {
	private static Logger logger = LoggerFactory.getLogger(JsonContainer.class);
	
	public static final String ServicePackageName = "com.strong.service.";

	public String clazz;

	public String getClazz() {
		return clazz;
	}

	public void setClass(String clazz) {
		this.clazz = clazz;
	}

	public String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> parameters;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	private String getPackageClass() {
		return ServicePackageName + clazz;
	}

	public Object getObject() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> myClass = Class.forName(getPackageClass());
		Object object = myClass.newInstance();

		//Method method = ReflectionUtils.findMethod(myClass, action);
		Method method = findMethod(myClass, action);
		
		Object[] parameterValues = getParameterValues();

		return ReflectionUtils.invokeMethod(method, object, parameterValues);
	}

	private Object[] getParameterValues() throws ClassNotFoundException {
		String[] parameterNames = ReflectionUtil.getMethodParams(getPackageClass(), action);

		Object[] pValues = new Object[parameterNames.length];
		for (int i = 0; i < parameterNames.length; i++) {
			pValues[i] = parameters.get(parameterNames[i]);
		}

		if (pValues.length == 0) {
			return null;
		}
		return pValues;
	}
	
	private Method findMethod(Class<?> clazz, String methodName) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(methodName, "Method name must not be null");

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}

		return null;
	}

}
