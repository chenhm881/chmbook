package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.Article;
import com.example.springbootfreemarker.domain.ArticleEntity;
import com.example.springbootfreemarker.domain.ArticleRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper {

    int insertOrUpdate(ArticleEntity articleEntity);

    List<ArticleEntity> findAll(ArticleRequest articleRequest);

    Article find(Integer id);

}
