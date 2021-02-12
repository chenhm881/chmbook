package com.chm.book.blog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
    @RequestMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }

}
