//package com.chm.book.blog.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.jwt.crypto.sign.RsaVerifier;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//import java.security.interfaces.RSAPublicKey;
//
//
//@EnableResourceServer
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private UserLogoutSuccessHandler logoutSuccessHandler;
//
//    @Autowired
//    private RsaKeyProperties prop;
//
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.tokenStore(new JwtTokenStore(accessTokenConverter()));
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//
//            http
//                    .cors()
//                    .and()
//                    .csrf()
//                    .disable().authorizeRequests().anyRequest().authenticated();
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        RSAPublicKey publicKey = (RSAPublicKey) prop.getPublicKey();
//        final RsaVerifier verifier = new RsaVerifier(publicKey);
//        converter.setVerifier(verifier);
//        return converter;
//    }
//}
