package com.chm.book.blog.entityconvert;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleTags;
import org.springframework.stereotype.Service;

@Service
public class ArticleConvert {
    public Article covertToArticle(ArticleTags articleTags) {
        Article article = new Article();
        article.setId(articleTags.getId());
        article.setTitle(articleTags.getTitle());
        article.setAuthorId(articleTags.getAuthorId());
        article.setCategoryId(articleTags.getCategoryId());
        article.setLikeCount(articleTags.getLikeCount());
        article.setCreateDate(articleTags.getCreateDate());
        article.setSummary(articleTags.getSummary());
        article.setContent(articleTags.getContent());
        article.setContentHtml(articleTags.getContentHtml());
        article.setStatus(articleTags.getStatus());
        article.setWeight(articleTags.getWeight());
        article.setViewCounts(articleTags.getViewCounts());
        article.setCommentCounts(articleTags.getCommentCounts());
        return article;
    };
}
