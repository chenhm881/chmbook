package com.example.springbootjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
public class TestController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("test");
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Hello World");
        Map<String, String> Trains = Map.of("Train1", "Train1", "Train2", "Train2", "Train3", "Train3");
        model.addAttribute("trains", Trains.values().toArray());
        //return new ModelAndView("hello");
        return "hello";
    }
}
