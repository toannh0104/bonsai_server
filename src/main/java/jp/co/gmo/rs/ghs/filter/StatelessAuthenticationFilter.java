/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.service.TokenAuthenticationService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * StatelessAuthenticationFilter class
 *
 * @author LongVNH
 *
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService authenticationService;

    /**
     * Constructor
     */
    public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Authentication authentication = authenticationService.getAuthentication(httpRequest);

        // check if ajaxRequest
        boolean ajax = ConstValues.TokenAuthentication.XML_REQUEST
                .equals(httpRequest.getHeader(ConstValues.TokenAuthentication.X_REQUEST_HEADER_NAME));

        String servletPath = httpRequest.getServletPath();

        // Redirect if authentication is null, servletPath = API_SPEECH_UPLOAD
        if (authentication == null
                && (servletPath.contains(ConstValues.TokenAuthentication.API_SPEECH_UPLOAD)

                // Or ajax request, servletPath not equal API_ERROR, if equal API_ERROR do filter
                || (ajax && !servletPath.contains(ConstValues.TokenAuthentication.API_ERROR)))) {

            // send redirect
            ((HttpServletResponse) response).sendRedirect(
                    httpRequest.getContextPath() + ConstValues.TokenAuthentication.API_ERROR);
        } else {
            // set authen
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

}
