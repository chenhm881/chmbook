package com.chm.book.selenium.base;


import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



public class PageInterceptor implements InvocationHandler {

    private Object target;

    public PageInterceptor(Object target) {
       this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(target, args);
        return null;
    }
}
