package com.chm.book.blog.controller;

import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class IndexController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArticleService articleService;


    @RequestMapping("/hello")
    public String helloWorld(Authentication user) {
        //ticketService.findTicket("hello");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.find();
        return getValue;
    }

    @RequestMapping("/hi")
    public String hi(Authentication user) {
        //ticketService.findTicket("hello");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.select();
        return getValue;
    }
    @RequestMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }

}
