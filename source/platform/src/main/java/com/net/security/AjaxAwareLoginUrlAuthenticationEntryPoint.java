package com.net.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxAwareLoginUrlAuthenticationEntryPoint  extends LoginUrlAuthenticationEntryPoint {
    public void commence(final HttpServletRequest request, final HttpServletResponse response, 
    		final AuthenticationException  authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
           response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");//对于ajax请求不重定向  而是返回错误代码
        } else {
            super.commence(request, response, authException);
        }
    }
}