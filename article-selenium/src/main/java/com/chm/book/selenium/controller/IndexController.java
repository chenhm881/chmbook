package com.chm.book.selenium.controller;

import com.chm.book.selenium.base.Context;
import com.chm.book.selenium.base.PageUtil;
import com.chm.book.selenium.base.WebDriverUtil;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class IndexController {


    @RequestMapping("/hello")
    public String helloWorld() throws Exception {

        //ticketService.findTicket("hello");
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        WebDriverUtil.startWebDriver("phantomjs");
        PageUtil.getCurrentURL();
        return "getValue";
    }

}
