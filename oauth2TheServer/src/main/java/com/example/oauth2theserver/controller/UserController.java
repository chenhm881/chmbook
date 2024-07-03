package com.example.oauth2theserver.controller;


import com.example.oauth2theserver.config.RsaKeyProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;




@RestController
public class UserController {

    @Autowired
    private RsaKeyProperties prop;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @RequestMapping("/userTest")
    public Object getUserTest(Authentication authentication) {
        return authentication;
    }

    @RequestMapping("/user")
    public Object getUser(Authentication authentication, HttpServletRequest request)  {
        String header = request.getHeader("Authorization");
        String token = header.substring(header.lastIndexOf("bearer") + 8);

        RSAPrivateKey privateKey = (RSAPrivateKey) prop.getPrivateKey();
        RSAPublicKey publicKey = (RSAPublicKey) prop.getPublicKey();
        Object obj = Jwts.parser().setSigningKey(publicKey).build().parseClaimsJws(token);
        return obj;
    }

    @RequestMapping("oauth/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response, String url) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        new SecurityContextLogoutHandler().logout(request, response, authentication);
        try {
            System.out.println("referer" + request.getHeader("referer"));
            if (!StringUtils.isEmpty(url)) {
                response.sendRedirect(url);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
