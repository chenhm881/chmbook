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
import java.util.Optional;

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
        SysUser sysUser = userEntityService.findAuthenticationUser(authorization);
        if (Optional.ofNullable(articleRequest).isPresent()) {
            articleRequest.setAuthorId(sysUser.getId().longValue());
            articles = articleService.getUserArticles(authorization, articleRequest);
        } else {
            articles = articleService.getUserArticles(authorization, sysUser.getId());
        }
        return articles;

    }

}
