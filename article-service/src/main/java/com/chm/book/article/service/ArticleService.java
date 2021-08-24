package com.chm.book.article.service;

import com.chm.book.article.domain.Article;
import com.chm.book.article.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> getArticles() {
        return articleMapper.findAll();
    }

    public Article getArticle(Integer id) {
        return articleMapper.find(id);
    }

    public Integer save(Article article) {
        return articleMapper.insertOrUpdate(article);
    }
}
