package com.strong.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public class UserToken implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private long refreshTime;//login time
    private long expireInterval;//expire required time
    private String userId;
    private String userEmail;
    private Collection<UserAuthority> authorities;
    private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserToken(User user, long expireInterval){
        
        this.expireInterval = expireInterval;
        this.id = UUID.randomUUID().toString();
        this.refreshTime = System.currentTimeMillis();
        
        this.userId = user.getId();
        this.authorities = user.getAuthorities();//TODO: might need copy it.
        this.userEmail = user.getEmail();
        this.user=user;
    }
    
    /**
     * return true if token is not expired
     * @return
     */
    public boolean isValid(){
        return ((refreshTime + expireInterval) > System.currentTimeMillis()); 
    }
    
    public void refresh(){
        this.refreshTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public long getExpireInterval() {
        return expireInterval;
    }
    
    public Collection<UserAuthority> getAuthorities() {
        return authorities;
    }
    

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

	public String getUserEmail() {
		return userEmail;
	}
}
