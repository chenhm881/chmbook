package com.chm.book.article.service;

import com.chm.book.article.domain.Article;
import com.chm.book.article.domain.ArticleEntity;
import com.chm.book.article.domain.ArticleRequest;
import com.chm.book.article.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleEntity> getArticles() {
        return articleMapper.findAll();
    }

    public List<ArticleEntity> getArticlesByUser(Integer authorId) {
        return articleMapper.findByAuthorId(authorId);
    }

    public List<ArticleEntity> selectArticles(ArticleRequest articleRequest) {
        return articleMapper.findAll();
    }

    public List<ArticleEntity> selectArticlesByUser(ArticleRequest articleRequest) {
        return articleMapper.findAll();
    }

    public Article getArticle(Integer id) {
        return articleMapper.find(id);
    }

    public Integer save(ArticleEntity articleEntity) {
        return articleMapper.insertOrUpdate(articleEntity);
    }


}
