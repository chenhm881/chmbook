package com.chm.book.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomDispatchProperties customDispatchProperties;

    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("http://localhost:8771/logout")
                .permitAll();

//            http
//                    .cors()
//                    .and()
//                    .csrf()
//                    .disable()
//                    .cors()
//                    .and()
//                    .csrf()
//                    .disable()
//                    .formLogin().and()
//                    .logout().logoutSuccessUrl(customDispatchProperties.getRedirectUri()).logoutSuccessHandler(userLogoutSuccessHandler)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/articles/**", "/article/**", "/tags", "/categories", "/save", "/register", "/comments/**", "/comment/**",
//                            "/like/**", "/user/articles/**", "/user/article/**").permitAll()
//                    .anyRequest().authenticated();

    }

}

