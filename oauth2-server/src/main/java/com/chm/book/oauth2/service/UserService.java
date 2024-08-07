package com.chm.book.oauth2.service;

import com.chm.book.oauth2.domain.SysUser;
import com.chm.book.oauth2.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectSysUser(username);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(sysUser.getUsername(), bCryptPasswordEncoder.encode(sysUser.getPassword()), authorities);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public UserDetails loadUserByClientId(String clientId) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectSysUserByClientId(clientId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(sysUser.getUsername(), bCryptPasswordEncoder.encode(sysUser.getPassword()), authorities);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

}
