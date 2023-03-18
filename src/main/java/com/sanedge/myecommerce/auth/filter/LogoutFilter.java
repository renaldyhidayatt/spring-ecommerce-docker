package com.sanedge.myecommerce.auth.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutFilter extends AbstractAuthenticationProcessingFilter {
    public LogoutFilter(String url) {
        super(url);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res)
            throws AuthenticationException, IOException {
        req.getSession().invalidate();
        res.getWriter().println(true);

        return null;
    }
}
