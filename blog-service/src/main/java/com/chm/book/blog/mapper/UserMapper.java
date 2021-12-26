package com.chm.book.blog.mapper;


import com.chm.book.blog.domain.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    SysUser findByUsername(String username);
    SysUser findById(Integer id);
    List<SysUser> findByIds(List<Integer> ids);
    int insertOrUpdate(SysUser sysUser);
}
