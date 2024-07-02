package com.chm.book.blog.controller;

import com.chm.book.blog.domain.SysUser;
import com.chm.book.blog.entityservice.UserEntityService;
import com.chm.book.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin
    @RequestMapping("/register")
    public ResponseEntity<Map<String,Object>> register(HttpServletRequest request, @RequestBody SysUser sysUser) {
        int retInt  = userEntityService.register(sysUser);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", retInt > 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
        responseMap.put("data", sysUser);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }
    @RequestMapping("/user")
    public ResponseEntity<Map<String,Object>> getUser(Authentication authentication, HttpServletRequest request) {
        String authorization =  request.getHeader("authorization");
        SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", Optional.ofNullable(sysUser).isPresent() ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED);
        responseMap.put("data", sysUser);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

//    @CrossOrigin
//    @RequestMapping("/logout")
//    public void getLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication getAuthentication = securityContext.getAuthentication();
//        new SecurityContextLogoutHandler().logout(request, response, getAuthentication);
//        restTemplate.getForObject("http://localhost:8771/logout?loginurl=http://localhost:8381/login", String.class);
//        response.sendRedirect("http://localhost:8181/authorize/login");
//    }
}
