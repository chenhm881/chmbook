package com.chm.book.article.mapper;

import com.chm.book.article.domain.ArticleTag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleTagMapper {
    List<ArticleTag> findAll(Integer articleId);
    List<ArticleTag> find(Integer articleId);
    int insert(Integer articleId, Integer tagId);
    int insertList(List<ArticleTag> articleTagList);
    int updateList(List<ArticleTag> articleTagList);
}
