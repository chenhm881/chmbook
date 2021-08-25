package com.chm.book.article.controller;

import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.Article;
import com.chm.book.article.domain.TBlog;
import com.chm.book.article.service.ArticleService;
import com.chm.book.article.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import rx.Observer;

import java.util.*;

@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ArticleService articleService;

    @CrossOrigin
    @RequestMapping("hello")
    public String helloWorld() {
        //int a = 5/0;
        return "Hello World";
    }

    @RequestMapping("find")
    public String getBlog()
    {

//        BlogCommandSemaphore command = new BlogCommandSemaphore(blogService, 62);
//        List<TBlog> tBlogs = command.execute();
//        //List<TBlog> tBlogs = blogService.getTBlog(62);
//        if(tBlogs.size() > 0) {
//        return "successfully";}
//        else {
//            return "failure";
//        }

        return "hello article";

    }

    @RequestMapping("articles")
    public List<Article> getArticles() {
        List<Article> articles = articleService.getArticles();
        return articles;
    }

    @RequestMapping("article/{id}")
    public Article getArticle(@PathVariable Integer id)
    {
        Article article = articleService.getArticle(id);
        return article;
    }

    @CrossOrigin
    @RequestMapping("saveArticle")
    public ResponseEntity<Map<String,Object>> saveArticle(@RequestBody Article article, @RequestParam List<Integer> tags) {

        Integer returnInt = articleService.save(article);
        Map<String, Object> responseMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> articleEntity;
        if(returnInt > 0) {
            responseMap.put("status", HttpStatus.OK.value());
            responseMap.put("data", article);
            responseMap.put("message", "message1");
            articleEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
            responseMap.put("message", "Save failed");
            articleEntity = new ResponseEntity<>(responseMap, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return articleEntity;
    }

    @RequestMapping("select")
    public String getCacheBlog()
    {
        final List<TBlog>[] list = new List[]{new ArrayList<>()};

        BlogHystrixObservableCommand observableCommand = new BlogHystrixObservableCommand(blogService);
        observableCommand.setId(62);
        Observable<List<TBlog>> observe = observableCommand.observe();
        observe.subscribe(new Observer<>() {

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
