package com.chm.book.oauth2.service;

import com.chm.book.oauth2.domain.SysUser;
import com.chm.book.oauth2.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectSysUser(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(sysUser.getAuthorities().split(",")).forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority));
        });
        User user = new User(sysUser.getUsername(), bCryptPasswordEncoder.encode(sysUser.getPassword()), authorities);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public UserDetails loadUserByClientId(String clientId) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectSysUserByClientId(clientId);
        sysUser.setPassword("password");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(sysUser.getAuthorities().split(",")).forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority));
        });
        User user = new User(sysUser.getUsername(), bCryptPasswordEncoder.encode(sysUser.getPassword()), authorities);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }


}
