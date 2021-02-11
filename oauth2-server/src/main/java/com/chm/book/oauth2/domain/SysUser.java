package com.chm.book.oauth2.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Data
public class SysUser {

    private Integer id;

    private String username;

    private String password;

    private String email;
}

