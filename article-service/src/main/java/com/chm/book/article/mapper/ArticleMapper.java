package com.chm.book.article.mapper;

import com.chm.book.article.domain.Article;
import com.chm.book.article.domain.ArticleEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper {

    int insertOrUpdate(ArticleEntity articleEntity);

    List<ArticleEntity> findAll();

    Article find(Integer id);

}
