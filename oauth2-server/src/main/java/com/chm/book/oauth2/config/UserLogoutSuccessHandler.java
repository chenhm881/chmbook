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
//        String[] arr = authorization.split(" ");
//
//
//
        OAuth2AccessToken authen = new JwtTokenStore(jwtAccessTokenConverter).readAccessToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MzgwNzM2MjAsInVzZXIiOnsicGFzc3dvcmQiOm51bGwsInVzZXJuYW1lIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJhY2NvdW50Tm9uRXhwaXJlZCI6dHJ1ZSwiYWNjb3VudE5vbkxvY2tlZCI6dHJ1ZSwiY3JlZGVudGlhbHNOb25FeHBpcmVkIjp0cnVlLCJlbmFibGVkIjp0cnVlfSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImZjNWVjNjE4LTMyZTktNGUxOC1hNTk2LWQxMWIyODI5OGEyNCIsImNsaWVudF9pZCI6ImJsb2cifQ.a_26_OIDAsZb3s9Z2YcIwl8OtN0ihC0psdNjxKziHdDfy4BcS1OjyLw8-CAUHjW5HpFl8PcRtgG-UO2EAKIkbXR67tuGhnFdZt_muPUL3L87hCT7S54vkgsHY_2urMACgwx8R60kktwGAXW2Mf9mh6i0KMaRPEeBwCrld_o__yszB8R1p7PNYrTpbT4Nft7wlbM9giZldSsi5MQJT7ATeE4ea8U-tOqjQRkurakxagv96_Xn7XAWoosFRd_VVXmCHIiWQaEPWDq-Uk6ea6I8MOnFvGrNu332hOmYseDwfviaUV4e65fsLNs5sGc6DbxxVAy0WnglnMi8IeHWuWNMqw");
//        if(authen == null)
//        {
//        }
//        authen.getAdditionalInformation();

        response.sendRedirect(loginUrl);
    }
}
