package com.strong.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.strong.model.entity.UserAuthority;
import com.strong.model.entity.UserToken;


public class LoginResponse {
	private UserDetail userDetail;

//	private List<UserRoleModel> roles = new ArrayList<UserRoleModel>();

	public LoginResponse(String username, UserToken token, Collection<UserAuthority> authorities) {
		this.userDetail = new UserDetail();
		userDetail.setToken(token.getId());
		userDetail.setEmail(token.getUserEmail());
		userDetail.setUsername(username);
//		for (UserAuthority authority : authorities) {
//			roles.add(new UserRoleModel(authority.getAuthority(), authority.getDescription()));
//		}
	}

	public LoginResponse(String username, UserToken token) {
		this.userDetail = new UserDetail();
		userDetail.setToken(token.getId());
		userDetail.setEmail(token.getUserEmail());
		userDetail.setUsername(username);

	}
	
	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

//	public List<UserRoleModel> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(List<UserRoleModel> roles) {
//		this.roles = roles;
//	}
}

class UserDetail {
	private String username;
	private String token;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
