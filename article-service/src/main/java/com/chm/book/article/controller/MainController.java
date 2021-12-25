package com.chm.book.article.controller;

import com.chm.book.article.command.BlogHystrixObservableCommand;
import com.chm.book.article.domain.*;
import com.chm.book.article.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

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

    @RequestMapping("articles")
    public List<ArticleEntity> getArticles() {
        List<ArticleEntity> entities = articleService.getArticles();
        return entities;
    }

    @RequestMapping("articles/query")
    public List<ArticleEntity> getArticles(@RequestBody ArticleRequest articleRequest) {
        List<ArticleEntity> entities = articleService.getArticles(articleRequest);
        return entities;
    }

    @RequestMapping("/user/articles")
    public List<ArticleEntity> getArticlesByUser(@RequestParam Integer authorId) {
        List<ArticleEntity> entities = articleService.getArticlesByUser(authorId);
        return entities;
    }

    @RequestMapping("/user/articles/query")
    public List<ArticleEntity> getArticlesByUser(@RequestBody ArticleRequest articleRequest) {
        List<ArticleEntity> entities = articleService.getArticlesByUser(articleRequest);
        return entities;
    }

    @RequestMapping("article/{id}")
    public Article getArticle(@PathVariable Integer id)
    {
        Article article = articleService.getArticle(id);
        return article;
    }

    @CrossOrigin
    @RequestMapping("article/save")
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
        tags.stream().forEach(tagId -> {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setId(tagId);
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

}
