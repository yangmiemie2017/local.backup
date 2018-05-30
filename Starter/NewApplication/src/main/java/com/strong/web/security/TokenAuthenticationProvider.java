package com.strong.web.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.strong.model.entity.UserToken;
import com.strong.service.AuthTokenService;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

	@Autowired
	private AuthTokenService authTokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String tokenId = authentication.getName();
		String password = (String) authentication.getCredentials();

		logger.info(
				"TokenAuthenticationProvider.class: authenticate() - tokenId:" + tokenId + "::password:" + password);

		UserToken token = null;
		try {
			token = authTokenService.loadTokenById(tokenId);
		} catch (UsernameNotFoundException e) {
			throw new BadCredentialsException("Username not found.");
		}

		if (token == null) {
			throw new BadCredentialsException("Username not found.");
		}

		Collection<? extends GrantedAuthority> authorities = token.getAuthorities();
		return new UsernamePasswordAuthenticationToken(token, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}
