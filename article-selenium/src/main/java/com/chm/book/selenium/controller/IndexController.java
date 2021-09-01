package com.chm.book.selenium.controller;

import com.chm.book.selenium.base.PageUtil;
import com.chm.book.selenium.base.WebDriverUtil;
import com.chm.book.selenium.pages.BlogPage;
import com.chm.book.selenium.service.BlogContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class IndexController {

    @Autowired
    private BlogContent blogContent;


    @RequestMapping("/hello")
    public String helloWorld() throws Exception {

        //ticketService.findTicket("hello");
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String content = blogContent.getContent();
        return content;
    }

}
