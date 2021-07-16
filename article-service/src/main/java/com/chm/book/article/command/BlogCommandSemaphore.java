package com.chm.book.article.command;

import com.chm.book.article.domain.TBlog;
import com.chm.book.article.service.BlogService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class BlogCommandSemaphore extends HystrixCommand<List<TBlog>> {

    private BlogService blogService;

    private Integer id;

    public BlogCommandSemaphore(BlogService blogService, Integer id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("blogService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getTBlog"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(10)
                .withCircuitBreakerSleepWindowInMilliseconds(50000)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(3)));

        this.blogService = blogService;
        this.id = id;
    }

    @Override
    protected List<TBlog> run() throws Exception {
        System.out.println("It is run on run" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return blogService.getTBlog(id);
    }

    @Override
    protected List<TBlog> getFallback() {
        System.out.println("It is run on fallback" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ArrayList<>();
    }
}
