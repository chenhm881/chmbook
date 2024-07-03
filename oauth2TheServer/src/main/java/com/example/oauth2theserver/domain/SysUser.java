package com.example.oauth2theserver.domain;

import lombok.Data;

@Data
public class SysUser {

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String authorities;
}

