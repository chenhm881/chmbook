package com.chm.book.blog.service;

import com.chm.book.blog.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
@FeignClient(name="ZUUL-GATEWAY-USER", url="http://101.34.6.152:31072")
public interface UserService {

    @RequestMapping(value = "/register")
    public int register(SysUser sysUser);
}


