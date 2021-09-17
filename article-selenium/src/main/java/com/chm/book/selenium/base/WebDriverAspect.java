package com.chm.book.selenium.base;

import com.chm.book.selenium.pages.BlogPage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class WebDriverAspect {


    @Pointcut(value = "execution(* com.chm.book.selenium.pages.BlogPage.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void start(JoinPoint joinPoint) throws Exception {
        BlogPage blogPage = (BlogPage) joinPoint.getTarget();
        WebDriverUtil.startWebDriver("chrome");
    }

    @After("pointcut()")
    public void end(JoinPoint joinPoint) {
        BlogPage blogPage = (BlogPage) joinPoint.getTarget();
        WebDriverUtil.stopWebDriver();
    }
}
