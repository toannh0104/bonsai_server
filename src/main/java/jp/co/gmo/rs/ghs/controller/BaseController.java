/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.filter.UserAuthentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * BaseController
 *
 * @author LongVNH
 *
 */
public abstract class BaseController extends AbstractController {

    /**
     * Get cookie
     *
     * @param cookie name
     * @param request the request
     */
    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        return "";
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest paramHttpServletRequest,
            HttpServletResponse paramHttpServletResponse) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * setHeader
     * @param response
     * @param name
     * @param value
     */
    public void setHeader(HttpServletResponse response, String name, String value) {
        response.addHeader(name, value);
    }

    /**
     * setCookie
     * @param response
     * @param name
     * @param value
     */
    public void setCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
    }

    /**
     * @return the userSessionDto
     */
    public UserSessionDto getUserSessionDto() {
        UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getDetails();
        }

        return null;
    }

}
