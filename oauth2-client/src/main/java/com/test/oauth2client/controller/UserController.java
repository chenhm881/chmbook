package com.test.oauth2client.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    @RequestMapping("/user")
    public ResponseEntity<Map<String,Object>> getUser(Authentication authentication) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK);
        responseMap.put("data", authentication);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/login")
    public ResponseEntity<Map<String,Object>> userLogin(Authentication authentication) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK);
        responseMap.put("data", authentication);
        responseMap.put("message", "");
        ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return responseEntity;
    }


//    @RequestMapping("/logout")
//    public void getLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication getAuthentication = securityContext.getAuthentication();
//        new SecurityContextLogoutHandler().logout(request, response, getAuthentication);
//        response.sendRedirect("http://localhost:8771/logout?url=http://localhost:8381/login");
//    }

}
