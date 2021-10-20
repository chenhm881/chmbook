package com.chm.book.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;



@EnableResourceServer
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserLogoutSuccessHandler logoutSuccessHandler;


    private static final String RESOURCE_ID = "blog-service";


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                //.logout().logoutSuccessUrl("http://localhost:8771/logout")
                //.and()
                .authorizeRequests()
                .antMatchers("/articles", "/article/**", "/tags", "/categories","/authorize/login**").permitAll()
                .anyRequest().authenticated();
    }
}
