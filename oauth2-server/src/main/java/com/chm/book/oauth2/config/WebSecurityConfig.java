package com.chm.book.oauth2.config;

import com.chm.book.oauth2.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**", "/user")
                .permitAll().anyRequest().authenticated()
                .and().formLogin()
                .and()
                .logout()
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and().csrf().disable();
//        http
//
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/user", "/check_user","/oauth/**", "/getToken", "/register").permitAll()
//                .and()
//                .logout().logoutSuccessHandler(userLogoutSuccessHandler)
//                .and()
//                .formLogin().loginProcessingUrl("/login").and().authorizeRequests()
//                .anyRequest().authenticated();
//        //http.sessionManagement().invalidSessionUrl("/login");
//        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);
    }

}
