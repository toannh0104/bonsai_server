/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.filter;

import java.util.Collection;

import jp.co.gmo.rs.ghs.dto.UserSessionDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * UserAuthentication class
 *
 * @author LongVNH
 *
 */
@SuppressWarnings("serial")
public class UserAuthentication implements Authentication {
    private final UserSessionDto user;
    private boolean authenticated = true;

    public UserAuthentication(UserSessionDto user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserSessionDto getDetails() {
        return user;
    }

    @Override
    public String getPrincipal() {
        return user.getUserName();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

}
