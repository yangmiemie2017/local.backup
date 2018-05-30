package com.strong.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Customized filter
 * 
 * @see BasicAuthenticationFilter
 * 
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private boolean ignoreFailure = false;
    private String credentialsCharset = "UTF-8";

    public AuthenticationFilter(AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");

        if (!isIgnoreFailure()) {
            Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
        }
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final boolean debug = logger.isDebugEnabled();

        try {

            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Token ")) {

                throw new AuthenticationCredentialsNotFoundException("Missing Authorization Token in head");
            }

            String token = extractAndDecodeHeader(header, request);

            if (debug) {
                logger.debug("Token Authentication Authorization header found for token '" + token + "'");
            }

            if (authenticationIsRequired(token)) {
                // TODO: temporary solution for use
                // UsernamePasswordAuthenticationToken, but we already set
                // password as blank
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(token, "");
                
                
                
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                Authentication authResult = authenticationManager.authenticate(authRequest);

                if (debug) {
                    logger.debug("Authentication success: " + authResult);
                }

                SecurityContextHolder.getContext().setAuthentication(authResult);

                rememberMeServices.loginSuccess(request, response, authResult);

                onSuccessfulAuthentication(request, response, authResult);
            }

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (debug) {
                logger.debug("Authentication request for failed: " + failed);
            }

            rememberMeServices.loginFail(request, response);

            onUnsuccessfulAuthentication(request, response, failed);

            if (ignoreFailure) {
                chain.doFilter(request, response);
            } else {
                authenticationEntryPoint.commence(request, response, failed);
            }

            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Decodes the header into the token
     *
     * @throws BadCredentialsException
     *             if the Basic header is not present or is not valid Base64
     */
    private String extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] decoded = header.substring(6).getBytes("UTF-8");
        String token = new String(decoded, getCredentialsCharset(request));
        return token;
    }

    private boolean authenticationIsRequired(String token) {
        // Only reauthenticate if token doesn't match SecurityContextHolder and
        // token
        // isn't authenticated
        // (see SEC-53)
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        // Limit username comparison to providers which use usernames (ie
        // UsernamePasswordAuthenticationToken)
        // (see SEC-348)

        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(token)) {
            return true;
        }

        // Handle unusual condition where an AnonymousAuthenticationToken is
        // already
        // present
        // This shouldn't happen very often, as BasicProcessingFitler is meant
        // to be
        // earlier in the filter
        // chain than AnonymousAuthenticationFilter. Nevertheless, presence of
        // both an
        // AnonymousAuthenticationToken
        // together with a BASIC authentication request header should indicate
        // reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with
        // that
        // provided by form and digest,
        // both of which force re-authentication if the respective header is
        // detected (and
        // in doing so replace
        // any existing AnonymousAuthenticationToken). See SEC-610.
        if (existingAuth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException {
    }

    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    protected boolean isIgnoreFailure() {
        return ignoreFailure;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
        this.rememberMeServices = rememberMeServices;
    }

    public void setCredentialsCharset(String credentialsCharset) {
        Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
        this.credentialsCharset = credentialsCharset;
    }

    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return credentialsCharset;
    }
}
