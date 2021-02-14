package com.chm.book.article.controller;

import com.chm.book.article.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("hello")
    public String helloWorld() {
        return "Hello World";
    }

    @RequestMapping("find")
    public String getBlog()
    {
        blogService.getTBlog(62);
        return "successfully";

    }

    @RequestMapping("select")
    public void getCacheBlog()
    {
        blogService.getCacheTBlog(2);
    }
}
