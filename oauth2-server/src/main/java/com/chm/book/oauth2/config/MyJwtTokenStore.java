package com.chm.book.oauth2.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class MyJwtTokenStore  extends JwtTokenStore {
    public MyJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
       super.storeAccessToken(token, authentication);
    }
}
