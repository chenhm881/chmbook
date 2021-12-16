package com.chm.book.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class KeyUserFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(KeyUserFilter.class);
    @Override
    public Object run() {
        // TODO Auto-generated method stub
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest req = (HttpServletRequest)RequestContext.getCurrentContext().getRequest();
        String authorization = req.getHeader("authorization");
        requestContext.addZuulRequestHeader("authorization",authorization);
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return "pre"; //pre、route、post、error
    }
}
