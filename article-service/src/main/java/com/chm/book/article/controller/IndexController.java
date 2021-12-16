package com.chm.book.article.controller;

import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.*;
import com.chm.book.article.service.*;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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

    @Autowired
    private ArticleTagService articleTagService;

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


    @RequestMapping("/user/articles")
    public List<ArticleEntity> getArticlesByUser(Integer authorId) {
        List<ArticleEntity> entities = articleService.getArticlesByUser(authorId);
        return entities;
    }

    @RequestMapping("article/{id}")
    public Article getArticle(@PathVariable Integer id)
    {
        Article article = articleService.getArticle(id);
        return article;
    }

    @CrossOrigin
    @RequestMapping("save")
    public ResponseEntity<Map<String,Object>> save(@RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags) {

        Integer returnInt = articleService.save(articleEntity);
        Integer insertInt = articleTagService.insert(articleEntity.getId(), tags);

        Article article = new Article();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(articleEntity.getCategoryId());
        article.setCategory(categoryEntity);
        article.setContent(articleEntity.getContent());
        article.setContentHtml(articleEntity.getContentHtml());
        article.setSummary(articleEntity.getSummary());
        article.setTitle(articleEntity.getTitle());
        article.setId(articleEntity.getId());
        List<TagEntity> tagEntities = new ArrayList<>();
        tags.stream().forEach(tag -> {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTagId(tag);
            tagEntities.add(tagEntity);
        });
        article.setTags(tagEntities);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseEntity<Map<String,Object>> responseEntity;
        if( returnInt > 0) {
            responseMap.put("status", HttpStatus.OK.value());
            responseMap.put("data", article);
            responseMap.put("message", "save successfully");
            responseEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
            responseMap.put("message", "save failed");
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
