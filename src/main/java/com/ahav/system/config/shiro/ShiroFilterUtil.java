package com.ahav.system.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class ShiroFilterUtil extends FormAuthenticationFilter {

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login submission detected.  Attempting to execute login.");
//                }
                return executeLogin(request, response);
            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login page view.");
//                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
//            if (log.isTraceEnabled()) {
//                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
//                        "Authentication url [" + getLoginUrl() + "]");
//            }
//
//            saveRequestAndRedirectToLogin(request, response);
        	response.getWriter().print("relogin");
    		response.getWriter().flush();
            return false;
        }
    }
}
