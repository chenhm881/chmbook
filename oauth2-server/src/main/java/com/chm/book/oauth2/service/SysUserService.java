package com.chm.book.oauth2.service;

import com.chm.book.oauth2.domain.SysUser;
import com.chm.book.oauth2.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public int insert(SysUser sysUser) {

        Integer retInt = sysUserMapper.insert(sysUser);
        sysUserMapper.addToBlogUserGroup(sysUser.getId(), 2);
        return retInt;
    }

}
