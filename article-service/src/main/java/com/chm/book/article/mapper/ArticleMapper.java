package com.chm.book.article.mapper;

import com.chm.book.article.domain.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper {

    int insertOrUpdate(Article article);

    List<Article> findAll();

    Article find(Integer id);

}
