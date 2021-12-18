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
        ArticleRequest articleRequest = new ArticleRequest();
        return articleMapper.findAll(articleRequest);
    }

    public List<ArticleEntity> getArticles(ArticleRequest articleRequest) {
        return articleMapper.findAll(articleRequest);
    }

    public List<ArticleEntity> getArticlesByUser(Integer authorId) {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setAuthorId(authorId.longValue());
        return articleMapper.findAll(articleRequest);
    }

    public List<ArticleEntity> getArticlesByUser(ArticleRequest articleRequest) {
        return articleMapper.findAll(articleRequest);
    }

    public Article getArticle(Integer id) {
        return articleMapper.find(id);
    }

    public Integer save(ArticleEntity articleEntity) {
        return articleMapper.insertOrUpdate(articleEntity);
    }


}
