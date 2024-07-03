package com.test.oauth2client.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SsoLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //response.sendRedirect("http://localhost:8771/oauth/exit?url=http://localhost:8381/login&accessToken=" + ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue());
        response.sendRedirect("http://localhost:8771/logout?url=http://localhost:8381/login&accessToken=" + ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue());
    }
}
