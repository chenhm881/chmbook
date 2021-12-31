package com.chm.book.oauth2.mapper;

import com.chm.book.oauth2.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {

    SysUser selectSysUser(String username);
    SysUser selectSysUserByClientId(String clientId);
    int insert(SysUser sysUser);
    int addToBlogUserGroup(Integer userId, Integer groupId);
}
