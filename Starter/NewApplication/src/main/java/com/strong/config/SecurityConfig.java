package com.strong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.strong.web.security.AuthenticationFilter;
import com.strong.web.security.TokenAuthenticationProvider;

/**
 * SecurityConfig
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private HttpStatusEntryPoint httpStatusEntryPoint = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
    
    //declare the static pages that no authority can visit
    @Override
    public void configure(WebSecurity security){
        security.ignoring()
            .antMatchers("/**/*.html")
            .antMatchers("/**/*.js")
            .antMatchers("/favicon.ico")
            .antMatchers("/**/*.css")
            .antMatchers("/images/**")
            .antMatchers("/fonts/**")
            .antMatchers("/css/**")
            .antMatchers("/Json/**")
            .antMatchers("/webjars/**")
            .antMatchers("/login/**")
            .antMatchers("/regist/**")
            .antMatchers("/validPwd*/**")
            .antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        
        
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), httpStatusEntryPoint);
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
        
        http.authorizeRequests()
            //.antMatchers("/books/**").hasAnyAuthority("ROLE_USER")
            //.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
            .antMatchers("/login").permitAll()
            .antMatchers("/regist").permitAll()
            .anyRequest().authenticated();

    }
}
