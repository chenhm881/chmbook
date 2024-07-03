package com.example.springbootfreemarker.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Controller
public class TestController {


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String test(Model model) {
        model.addAttribute("name", "Hello World");
        return "hello";
    }

    @RequestMapping("/test")
    public String test(Date date) {
        //return new ModelAndView("test");
        return "jsp/test";
    }

    @RequestMapping("/he")
    public String getHe(Model model) {
        model.addAttribute("he", "Hello World");
        return "thymeleaf/he";
    }

}
