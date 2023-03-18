package com.sanedge.myecommerce.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import static com.sanedge.myecommerce.utils.Constants.*;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanedge.myecommerce.dto.LoginUserDto;
import com.sanedge.myecommerce.dto.UserLoginResponseDto;
import com.sanedge.myecommerce.entity.ApplicationUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    public LoginFilter(String url, AuthenticationManager authManager) {
        super(url);
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res)
            throws AuthenticationException, IOException {
        LoginUserDto loginUserDto = new ObjectMapper()
                .readValue(req.getInputStream(), LoginUserDto.class);

        return getAuthenticationManager()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginUserDto.getUsername(),
                                loginUserDto.getPassword()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth)
            throws IOException, ServletException {
        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

        req.getSession().setAttribute(UserSessionKey, applicationUser);

        res.setContentType("application/json");
        res.getOutputStream().print(UserLoginResponseDto.toJSON(applicationUser));
    }
}
