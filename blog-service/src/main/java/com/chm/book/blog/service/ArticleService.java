package com.chm.book.blog.service;

import com.chm.book.blog.domain.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(name="ZUUL-GATEWAY-ARTICLE", url="http://101.34.6.152:31072", fallbackFactory = ArticleServiceImp.class)
public interface ArticleService {

    @RequestMapping(value = "/api/articles")
    public List<ArticleEntity> getArticles();

    @PostMapping(value = "/api/article/{id}")
    public Article getArticle(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/api/save")
    public ResponseEntity<Map<String,Object>> save(@RequestHeader String authorization, @RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags);

    @RequestMapping(value = "/api/tags")
    public List<TagEntity> getTags();

    @RequestMapping(value = "/api/categories")
    public List<CategoryEntity> getCategories();

}


