package com.chm.book.article.controller;

import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.*;
import com.chm.book.article.service.ArticleService;
import com.chm.book.article.service.BlogService;
import com.chm.book.article.service.CategoryService;
import com.chm.book.article.service.TagService;
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

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

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
    public List<ArticleEntity> getArticles() {
        List<ArticleEntity> entities = articleService.getArticles();
        return entities;
    }

    @RequestMapping("article/{id}")
    public Article getArticle(@PathVariable Integer id)
    {
        Article article = articleService.getArticle(id);
        return article;
    }

    @CrossOrigin
    @RequestMapping("saveArticle")
    public ResponseEntity<Map<String,Object>> saveArticle(@RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags) {

        Integer returnInt = articleService.save(articleEntity);
        Map<String, Object> responseMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity;
        if(returnInt > 0) {
            responseMap.put("status", HttpStatus.OK.value());
            responseMap.put("data", articleEntity);
            responseMap.put("message", "message1");
            responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
            responseMap.put("message", "Save failed");
            responseEntity = new ResponseEntity<>(responseMap, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
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

    @RequestMapping("tags")
    public List<TagEntity> getTags() {
        List<TagEntity> tags = tagService.getTags();
        return tags;
    }

    @RequestMapping("categories")
    public List<CategoryEntity> getCategories() {
        List<CategoryEntity> categories = categoryService.getCategories();
        return categories;
    }

}
