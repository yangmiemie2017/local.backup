package com.strong.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.strong.model.entity.Menu;
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
import com.strong.web.menu.MenuSettingHelper;
import com.strong.web.model.LoginResponse;
import com.strong.common.Data;

@RestController
public class MenuController {
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getMenuAction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserToken token = (UserToken) authentication.getPrincipal();
        
        Menu[] menu=MenuSettingHelper.getMenu();
        
        updateMenuAccess(menu, token);
        
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(menu);
    }
    
    private static void updateMenuAccess(Menu[] menu, UserToken token)
    {
    	List<String> access=new ArrayList<String>();
    	token.getAuthorities().forEach(x->access.add(x.getAuthority()));
    	setMenuDisplay(menu, access);
    }	
    
	private static int setMenuDisplay(Menu[] menu, List<String> access) {
		int displayMenuCount = 0;
		for (Menu m : menu) {
			if(access.contains(m.getMenuCode())){
				m.setDisplay(true);
				displayMenuCount++;
			}
			Menu[] sub = m.getSubNav();
			if (null != sub) {
				// if sub menu display, then the parent menu also display.
				if (setMenuDisplay(sub, access) > 0) {
					m.setDisplay(true);
				}
			}

			if (m.getRoute() == "home") {// always display home
				m.setDisplay(true);
			}
		}
		return displayMenuCount;
	}
}
