package com.strong.web.model;



public class RegisterRequest {
	private String userName;
	private String password;
	private String email;
	private String gender;
	private String signature;
	
	private String soeid;

	
	public String getSoeid() {
		return soeid;
	}

	public void setSoeid(String soeid) {
		this.soeid = soeid;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
