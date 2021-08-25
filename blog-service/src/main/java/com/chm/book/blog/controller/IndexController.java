package com.chm.book.blog.controller;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleTags;
import com.chm.book.blog.entityconvert.ArticleConvert;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class IndexController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleConvert articleConvert;

    @RequestMapping("/hello")
    public String helloWorld() {
        //ticketService.findTicket("hello");
        //OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.find();
        return getValue;
    }

    @RequestMapping("/hi")
    public String hi(Authentication user) {
        //ticketService.findTicket("hello");
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)user.getDetails();
        String getValue = articleService.addFiles();
        return getValue;
    }
    @RequestMapping("/user")
    public Authentication getUser(Authentication user) {
        return user;
    }

    @CrossOrigin
    @RequestMapping("/articles")
    public ResponseEntity<Map<String,Object>> getArticles( @RequestBody Map<String, Object> params) {
//        List<Article> articles = new ArrayList<>();
//        Article article = new Article();
//        article.setId(1);
//        article.setContent("article 1");
//        articles.add(article);
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("status", HttpStatus.OK.value());
//        responseMap.put("data", articles);
//        responseMap.put("message", "message1");
//        responseMap.put("tags", new ArrayList<>());
//        ResponseEntity<Map<String,Object>> articleEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);

        List<Article> articles = articleService.getArticles();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", articles);
        responseMap.put("message", "message1");
        ResponseEntity<Map<String,Object>> articleEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return articleEntity;
    }

    @CrossOrigin
    @RequestMapping("/article/{id}")
    public ResponseEntity<Map<String,Object>> getArticle( @PathVariable Integer id) {
        Article article = articleService.getArticle(id);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("data", article);
        responseMap.put("message", "message1");
        responseMap.put("category", 1);
        responseMap.put("tags", new Integer[] {1, 2});
        ResponseEntity<Map<String,Object>> articleEntity = new ResponseEntity<>(responseMap, HttpStatus.OK);
        return articleEntity;
    }

    @CrossOrigin
    @RequestMapping("/saveArticle")
    public ResponseEntity<Map<String,Object>> saveArticle(@RequestBody ArticleTags articleTags) {
        Article article = articleConvert.covertToArticle(articleTags);
        List<Integer> tags = articleTags.getTags();
        ResponseEntity<Map<String,Object>> articleEntity  = articleService.saveArticle(article, tags);
        return articleEntity;
    }
}
