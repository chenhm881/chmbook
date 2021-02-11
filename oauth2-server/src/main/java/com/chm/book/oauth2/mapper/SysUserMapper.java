package com.chm.book.oauth2.mapper;

import com.chm.book.oauth2.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {

    SysUser selectSysUser(String username);
}