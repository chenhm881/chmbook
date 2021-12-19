package com.chm.book.blog.entityservice;

import com.chm.book.blog.domain.ArticleEntity;
import com.chm.book.blog.domain.ArticleRequest;
import com.chm.book.blog.domain.ArticleTags;
import com.chm.book.blog.domain.SysUser;
import com.chm.book.blog.mapper.UserMapper;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserEntityService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public SysUser findAuthenticationUser(String authorization) {
        Map<String, Object> resultMap = userService.findUser(authorization);
        String username = resultMap.get("user").toString();
        SysUser sysUser = userMapper.findByUsername(username);
        return sysUser;
    }

    public SysUser findById(Integer id) {
        SysUser sysUser = userMapper.findById(id);
        return sysUser;
    }

}
