/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

/**
 * create TokenAuthenticationService
 *
 * @author LongVNH
 *
 */
public interface TokenAuthenticationService {

    /**
     * @param request
     * @return Authentication
     */
    Authentication getAuthentication(HttpServletRequest request);
}
