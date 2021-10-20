package com.chm.book.blog.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private List<String> roles;
}

