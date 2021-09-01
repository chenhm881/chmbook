package com.chm.book.selenium.pages;

import com.chm.book.selenium.base.Context;
import com.chm.book.selenium.base.ElementUtil;
import com.chm.book.selenium.base.PageUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
public class BlogPage {

    public void getContent() {
        WebDriver webDriver = (WebDriver)Context.get("webDriver");
        PageUtil.navigateTo("http://www.baidu.com");
        By by = By.id("s-hotsearch-wrapper");
        WebElement webElement = ElementUtil.waitWebElement(webDriver, by, 80);
        String text = webElement.getText();
        System.out.println(text);
    }
}
