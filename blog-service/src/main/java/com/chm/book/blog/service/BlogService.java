package com.chm.book.blog.service;

import com.chm.book.blog.domain.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(name="ZUUL-GATEWAY", fallbackFactory = BlogServiceImp.class)
public interface BlogService {

    @PostMapping(value = "/article-service/api/find")
    public String find();

    @RequestMapping(value = "/article-service/api/articles")
    public List<ArticleEntity> getArticles();

    @PostMapping(value = "/article-service/api/article/{id}")
    public Article getArticle(@PathVariable(value = "id") Integer id);

    @PostMapping(value = "/article-service/api/saveArticle")
    public ResponseEntity<Map<String,Object>> saveArticle(@RequestHeader String authorization, @RequestBody ArticleEntity articleEntity, @RequestParam List<Integer> tags);

/*    @PostMapping(value = "/oauth2-service/exit")
    public ResponseEntity<Map<String,Object>> logout();*/

    @RequestMapping(value = "/uaa/register")
    public int register(SysUser sysUser);

    @RequestMapping(value = "/article-service/api/tags")
    public List<TagEntity> getTags();

    @RequestMapping(value = "/article-service/api/categories")
    public List<CategoryEntity> getCategories();

}


