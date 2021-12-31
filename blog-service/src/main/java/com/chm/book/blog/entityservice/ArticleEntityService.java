package com.chm.book.blog.entityservice;

import com.chm.book.blog.domain.*;
import com.chm.book.blog.mapper.UserMapper;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleEntityService {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private ArticleService articleService;


    public ArticleEntity createEntity(String authorization, ArticleTags articleTags) {
        SysUser sysUser = userEntityService.findAuthenticationUser(authorization);

        ArticleEntity entity = new ArticleEntity();
        entity.setId(articleTags.getId());
        entity.setTitle(articleTags.getTitle());
        entity.setAuthorId(articleTags.getAuthorId());
        entity.setCategoryId(articleTags.getCategoryId());
        entity.setLikeCounts(articleTags.getLikeCount());
        entity.setCreateDate(articleTags.getCreateDate());
        entity.setSummary(articleTags.getSummary());
        entity.setContent(articleTags.getContent());
        entity.setContentHtml(articleTags.getContentHtml());
        entity.setStatus(articleTags.getStatus());
        entity.setWeight(articleTags.getWeight());
        entity.setViewCounts(articleTags.getViewCounts());
        entity.setCommentCounts(articleTags.getCommentCounts());
        entity.setAuthorId(sysUser.getId().longValue());
        return entity;
    };

    public List<ArticleEntity> getArticles(ArticleRequest articleRequest) {
        List<ArticleEntity> articles = new ArrayList<>();
        if (Optional.ofNullable(articleRequest).isPresent()) {
            articles = articleService.getArticles(articleRequest);
        } else {
            articles = articleService.getArticles();
        }
        return articles;
    }

    public List<ArticleEntity> getUserArticles(String authorization, ArticleRequest articleRequest) {
        List<ArticleEntity> articles = new ArrayList<>();
        if (authorization.isEmpty()) return articles;
        SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
        if (Optional.ofNullable(articleRequest).isPresent()) {
            articleRequest.setAuthorId(sysUser.getId().longValue());
            articles = articleService.getUserArticles(authorization, articleRequest);
        } else {
            articles = articleService.getUserArticles(authorization, sysUser.getId());
        }
        return articles;

    }

    public ArticleResponse getArticle(Integer id) {
        Article article = articleService.getArticle(id);
        ArticleResponse articleResponse = new ArticleResponse();
        BeanUtils.copyProperties(article, articleResponse);
        SysUser user = userEntityService.findById(article.getAuthorId().intValue());
        articleResponse.setUser(user);
        return articleResponse;
    }

    public Article createResponseArticle(ArticleEntity articleEntity, List<Integer> tags) {
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
        return article;
    }


}
