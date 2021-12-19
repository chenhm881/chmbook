package com.chm.book.blog.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .formLogin().loginPage("/login").permitAll().and()
//                .authorizeRequests()
//                .antMatchers("/articles").permitAll()
//                .anyRequest().authenticated();
//            super.configure(http);
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .logout().logoutSuccessUrl("http://101.34.6.152:31073/logout").logoutSuccessHandler(new UserLogoutSuccessHandler())
                    .and()
                    .authorizeRequests()
                    .antMatchers("/articles/**", "/article/**", "/tags", "/categories", "/save", "/register", "/user/articles/**", "/user/article/**").permitAll()
                    .anyRequest().authenticated();

    }

}

