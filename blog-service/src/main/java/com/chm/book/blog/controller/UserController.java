package com.chm.book.blog.controller;

import com.chm.book.blog.domain.SysUser;
import com.chm.book.blog.entityservice.UserEntityService;
import com.chm.book.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityService userEntityService;

    @CrossOrigin
    @RequestMapping("/register")
    public ResponseEntity<Map<String,Object>> register(HttpServletRequest request, @RequestBody SysUser sysUser) {
        int response  = userEntityService.register(sysUser);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", response > 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
        responseMap.put("data", sysUser);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }
    @RequestMapping("/user")
    public ResponseEntity<Map<String,Object>> getUser(HttpServletRequest request) {
        String authorization =  request.getHeader("authorization");
        SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", Optional.ofNullable(sysUser).isPresent() ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
        responseMap.put("data", sysUser);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

}
