package com.chm.book.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/user", "/check_user","/oauth/**").permitAll()
                .and()
                .logout()
                .and()
                .formLogin().loginProcessingUrl("/login").and().authorizeRequests()
                .anyRequest().authenticated();
        http.sessionManagement().invalidSessionUrl("/login");
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);
    }

}
