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
        //requestContext.addZuulRequestHeader("test",header);
        //这里有个贼有意思的地方，在配置文件中像上文一样配置，单独把Authorization放开，不管你把什么参数作为key加入header,就像这里放test，依旧会当做放的是Authorization
        //在转发后的服务接收到的依旧是Authorization与它的值，test的值为空，感兴趣的可以试下

        return null;
    }

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return true; //表示是否需要执行该filter，true表示执行，false表示不执行
    }
    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return "pre"; //定义filter的类型，有pre、route、post、error四种
    }
}
