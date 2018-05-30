package com.strong.model.dto;

import com.strong.model.entity.User;
import com.strong.model.entity.UserAuthority;
//import lombok.Getter;
//import lombok.ToString;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;

/**
 * @author cy
 */
//@Getter
//@ToString
public class CustomUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 1702923242319850756L;

    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;
    private final Set<UserAuthority> authorities;
	private String username;
	private String password;
	
	
    public CustomUserDetails(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (user != null
                && user.getUsername()!=null
                && user.getPassword()!=null) {
            setUsername(user.getUsername());
            setPassword(user.getPassword());
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
//            this.authorities = Collections.unmodifiableSet(new HashSet<>(CollectionUtils.emptyIfNull(authorities)));
            this.authorities = new LinkedHashSet<UserAuthority>();
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }
    
	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "UserDetails [username=" + username
				+ ", password=" + password + ", enabled=" + enabled
				+ ", authorities=" + authorities + "]";
	}   
	
	
    

}
