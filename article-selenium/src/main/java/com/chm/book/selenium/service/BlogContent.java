package com.chm.book.selenium.service;

import com.chm.book.selenium.pages.BlogPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogContent {

    @Autowired
    private BlogPage blogPage;

    public String getContent() {
        blogPage.getContent();
        return "getContent";
    }
}
