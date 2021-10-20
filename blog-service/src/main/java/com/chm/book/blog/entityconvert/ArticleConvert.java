package com.chm.book.blog.entityconvert;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleEntity;
import com.chm.book.blog.domain.ArticleTags;
import org.springframework.stereotype.Service;

@Service
public class ArticleConvert {
    public ArticleEntity covertToArticle(ArticleTags articleTags) {
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
        return entity;
    };
}
