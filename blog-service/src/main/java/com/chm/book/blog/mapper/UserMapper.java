package com.chm.book.blog.mapper;


import com.chm.book.blog.domain.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    SysUser findUserByUsername(String username);
}
