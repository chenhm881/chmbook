package com.chm.book.blog.service;

import com.chm.book.blog.domain.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(name="ZUUL-GATEWAY-ARTICLE", url="ZUUL-GATEWAY", fallbackFactory = ArticleServiceImp.class)
public interface ArticleService {

    @PostMapping(value = "/article/api/article/{id}")
    public Article getArticle(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/article/api/article/save")
    public Integer save(@RequestHeader String authorization, @RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags);

    @RequestMapping(value = "/article/api/tags")
    public List<TagEntity> getTags();

    @RequestMapping(value = "/article/api/categories")
    public List<CategoryEntity> getCategories();

    @RequestMapping(value = "/article/api/articles")
    public List<ArticleEntity> getArticles();

    @RequestMapping(value = "/article/api/articles/query")
    List<ArticleEntity> getArticles(@RequestBody ArticleRequest articleRequest);

    @RequestMapping(value = "/article/api/user/articles")
    List<ArticleEntity> getUserArticles(@RequestHeader String authorization, @RequestParam Integer authorId);

    @RequestMapping(value = "/article/api/user/articles/query")
    List<ArticleEntity> getUserArticles(@RequestHeader String authorization, @RequestBody ArticleRequest articleRequest);

    @RequestMapping(value = "/article/api/comments/article")
    List<Comment> getArticleComments(@RequestParam Integer articleId);

    @RequestMapping(value = "/article/api/comment/save")
    Integer saveComment(@RequestHeader String authorization, Comment comment);

    @RequestMapping(value = "/article/api/like/query")
    LikeState getOneLike(@RequestHeader String authorization, @RequestParam Integer articleId, @RequestParam Integer authorId);

    @RequestMapping(value = "/article/api/like/save")
    ResponseEntity<Map<String,Object>> saveLike(@RequestHeader String authorization, @RequestBody LikeState likeState);



}


