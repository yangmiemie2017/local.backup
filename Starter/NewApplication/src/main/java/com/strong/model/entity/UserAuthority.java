package com.strong.model.entity;


import org.springframework.security.core.GrantedAuthority;


public class UserAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    

    private String id;
    

    private String authority;
    
    
    private String description;
    
    public UserAuthority(String id, String authority, String description) {
		super();
		this.id = id;
		this.authority = authority;
		this.description = description;
	}

	@Override
    public String getAuthority() {
        return authority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
