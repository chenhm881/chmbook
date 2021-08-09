package com.chm.book.files.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IndexController {

    @CrossOrigin
    @RequestMapping("hello")
    public String helloWorld() {
        //int a = 5/0;
        return "Hello World";
    }


}
