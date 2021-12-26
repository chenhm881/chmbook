package com.chm.book.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {


    @Autowired
    private RsaKeyProperties prop;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String loginUrl = request.getParameter("loginurl");

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        RSAPublicKey publicKey = (RSAPublicKey) prop.getPublicKey();
        final RsaVerifier verifier = new RsaVerifier(publicKey);
        jwtAccessTokenConverter.setVerifier(verifier);
        response.sendRedirect(loginUrl);
    }
}
