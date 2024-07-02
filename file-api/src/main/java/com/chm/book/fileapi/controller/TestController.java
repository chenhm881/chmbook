package com.chm.book.fileapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TestController {

    @RequestMapping("test")
    public String test(@RequestParam String mes) {
        return "index";
    }
}
