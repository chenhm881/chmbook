package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.ArticleTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleTagMapper {
    List<ArticleTag> findAll(Integer articleId);
    List<ArticleTag> find(Integer articleId);
    int insert(Integer articleId, Integer tagId);
    int insertList(List<ArticleTag> articleTagList);
    int updateList(@Param("list") List<ArticleTag> articleTagList, @Param("articleId") Integer articleId);
}
