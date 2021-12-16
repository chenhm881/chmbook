package com.chm.book.blog.entityservice;

import com.chm.book.blog.domain.ArticleEntity;
import com.chm.book.blog.domain.ArticleRequest;
import com.chm.book.blog.domain.ArticleTags;
import com.chm.book.blog.domain.SysUser;
import com.chm.book.blog.mapper.UserMapper;
import com.chm.book.blog.service.ArticleService;
import com.chm.book.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        entity.setLikeCount(articleTags.getLikeCount());
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

    public List<ArticleEntity> getArticles(String authorization) {
        List<ArticleEntity> articles = new ArrayList<>();
        if (StringUtils.isNotEmpty(authorization)){
            SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
            articles = articleService.getUserArticles(authorization, sysUser.getId());
        } else {
            articles = articleService.getArticles();
        }
        return articles;

    }

    public List<ArticleEntity> selectArticles(String authorization, ArticleRequest articleRequest) {
        List<ArticleEntity> articles = new ArrayList<>();
        if (StringUtils.isNotEmpty(authorization)){
            SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
            articleRequest.setAuthorId(sysUser.getId().longValue());
            articles = articleService.selectUserArticles(authorization, articleRequest);
        } else {
            articles = articleService.selectArticles(articleRequest);
        }
        return articles;

    }
}
