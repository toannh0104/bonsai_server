/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.filter.UserAuthentication;
import jp.co.gmo.rs.ghs.service.TokenAuthenticationService;
import jp.co.gmo.rs.ghs.util.StringUtils;
import jp.co.gmo.rs.ghs.util.TokenUtils;

import org.springframework.security.core.Authentication;

/**
 * create token service
 *
 * @author LongVNH
 *
 */
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    public Authentication getAuthentication(HttpServletRequest request) {
        // get token from header
        String token = request.getHeader(ConstValues.TokenAuthentication.AUTH_HEADER_NAME);

        // if null, continue get token from cookies
        if (StringUtils.isEmpty(token) && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(ConstValues.TokenAuthentication.AUTH_HEADER_NAME)) {
                    token = cookie.getValue();
                }
            }
        }

        // do authen
        if (!StringUtils.isEmpty(token)) {
            UserSessionDto userInfo = TokenUtils.validateToken(token);
            if (userInfo != null) {

                return new UserAuthentication(userInfo);
            }
        }
        return null;
    }

}
