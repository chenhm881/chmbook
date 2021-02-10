package com.chm.book.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @RequestMapping("hello")
    public String helloWorld() {
        return "Hello World";
    }
}
