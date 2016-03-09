/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.filter;

import jp.co.gmo.rs.ghs.service.TokenAuthenticationService;
import jp.co.gmo.rs.ghs.service.impl.LoginServiceImpl;
import jp.co.gmo.rs.ghs.service.impl.TokenAuthenticationServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurityConfig class
 *
 * @author LongVNH
 *
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginServiceImpl userService;

    private TokenAuthenticationServiceImpl tokenAuthenticationService;

    public SpringSecurityConfig() {
        super(true);
        this.userService = new LoginServiceImpl();
        tokenAuthenticationService = new TokenAuthenticationServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().disable()
                .authorizeRequests()

                // Allow anonymous resource requests
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                .antMatchers("/resources/**").permitAll()

                // Allow anonymous logins
                .antMatchers("/").permitAll()
                .antMatchers("/index.jsp").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/checkLogin").permitAll()

                // allow error
                 .antMatchers("/error").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()

                // Custom Token based authentication based on the header
                // previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public LoginServiceImpl userDetailsService() {
        return userService;
    }

    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }
}
