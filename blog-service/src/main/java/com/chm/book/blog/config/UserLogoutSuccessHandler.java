package com.chm.book.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private CustomDispatchProperties customDispatchProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        response.sendRedirect(customDispatchProperties.getAuth2LogoutUrl() + "?loginurl=" + customDispatchProperties.getRedirectUri());

        response.sendRedirect("http://localhost:8771/oauth/exit?url=http://localhost:8381/login&accessToken=" + ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue());
    }
}
