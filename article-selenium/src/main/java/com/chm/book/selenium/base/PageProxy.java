package com.chm.book.selenium.base;

import com.chm.book.selenium.pages.BasePage;
import com.chm.book.selenium.pages.BlogPage;


import java.lang.reflect.Proxy;

public class PageProxy {

    public void getContent() {

        BasePage basePage = new BlogPage();

        PageInterceptor pageInterceptor = new PageInterceptor(new BlogPage());

        BlogPage blogPage = (BlogPage) Proxy.newProxyInstance(basePage.getClass().getClassLoader(),
                basePage.getClass().getInterfaces(), pageInterceptor);
        basePage.getContent();

    }

}
