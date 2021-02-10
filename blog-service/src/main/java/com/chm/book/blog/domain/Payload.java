package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Payload<T> {
    private String id;
    private String clientId;
    private T sysUser;
    private Date expiration;
}
