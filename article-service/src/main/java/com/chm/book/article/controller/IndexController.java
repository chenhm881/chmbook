package com.chm.book.article.controller;

import com.chm.book.article.command.BlogCommandSemaphore;
import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.TBlog;
import com.chm.book.article.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @CrossOrigin
    @RequestMapping("hello")
    public String helloWorld() {
        //int a = 5/0;
        return "Hello World";
    }

    @RequestMapping("find")
    public String getBlog()
    {

        BlogCommandSemaphore command = new BlogCommandSemaphore(blogService, 62);
        List<TBlog> tBlogs = command.execute();
        //List<TBlog> tBlogs = blogService.getTBlog(62);
        if(tBlogs.size() > 0) {
        return "successfully";}
        else {
            return "failure";
        }

    }

    @RequestMapping("select")
    public String getCacheBlog()
    {
        final List<TBlog>[] list = new List[]{new ArrayList<>()};

        BlogHystrixObservableCommand observableCommand = new BlogHystrixObservableCommand(blogService);
        observableCommand.setId(62);
        Observable<List<TBlog>> observe = observableCommand.observe();
        observe.subscribe(new Observer<List<TBlog>>() {

                              @Override
                              public void onCompleted() {
                                  System.out.println("聚合完了所有的查询请求!");
                              }

                              @Override
                              public void onError(Throwable throwable) {
                                  throwable.printStackTrace();
                              }

                              @Override
                              public void onNext(List<TBlog> tBlogList) {
                                  list[0] = tBlogList;
                              }
                          });

        //blogService.getCacheTBlog(2);
        return "successfully";
    }
}
