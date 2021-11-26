package com.chm.book.blog.service;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleEntity;
import com.chm.book.blog.domain.CategoryEntity;
import com.chm.book.blog.domain.TagEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BlogServiceImp implements FallbackFactory<BlogService> {

    Logger logger = LoggerFactory.getLogger(BlogServiceImp.class);

    @Override
    public BlogService create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            logger.error(msg);
        }
        return new BlogService() {
            @Override
            public String find() {
                return null;
            }

            @Override
            public List<ArticleEntity> getArticles() {
                return null;
            }

            @Override
            public Article getArticle(Integer id) {
                return null;
            }

            @Override
            public ResponseEntity<Map<String, Object>> saveArticle(String authorization, ArticleEntity articleEntity, List<Integer> tags) {
                return null;
            }

            @Override
            public List<TagEntity> getTags() {
                return null;
            }

            @Override
            public List<CategoryEntity> getCategories() {
                return null;
            }
        };
    }

}
