package com.example.oauth2theserver.mapper;

import com.example.oauth2theserver.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    SysUser selectSysUser(String username);
    SysUser selectSysUserByClientId(String clientId);
    int insert(SysUser sysUser);
    int addToBlogUserGroup(Integer userId, Integer groupId);
}
