package com.chm.book.blog.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {//清除认证
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        response.sendRedirect(customDispatchProperties.getAuth2LogoutUrl() + "/logout?loginurl=" + customDispatchProperties.getRedirectUri());

    }
}
