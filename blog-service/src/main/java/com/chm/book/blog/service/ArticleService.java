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

    @RequestMapping(value = "/article/api/articles")
    public List<ArticleEntity> getArticles();

    @PostMapping(value = "/article/api/article/{id}")
    public Article getArticle(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/article/api/save")
    public ResponseEntity<Map<String,Object>> save(@RequestHeader String authorization, @RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags);

    @RequestMapping(value = "/article/api/tags")
    public List<TagEntity> getTags();

    @RequestMapping(value = "/article/api/categories")
    public List<CategoryEntity> getCategories();

}


