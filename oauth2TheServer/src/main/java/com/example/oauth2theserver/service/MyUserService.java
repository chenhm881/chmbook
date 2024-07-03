package com.example.oauth2theserver.service;

import com.example.oauth2theserver.domain.SysUser;
import com.example.oauth2theserver.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MyUserService implements UserDetailsService {

   @Autowired
   private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectSysUser(username);
        String password = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(sysUser.getAuthorities()));
    }
}
