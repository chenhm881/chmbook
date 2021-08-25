package com.chm.book.blog.service;

import com.chm.book.blog.domain.Article;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ArticleServiceImp implements FallbackFactory<ArticleService> {

    Logger logger = LoggerFactory.getLogger(ArticleServiceImp.class);

    @Override
    public ArticleService create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            logger.error(msg);
        }
        return (new ArticleService() {
            @Override
            public String find() {
                return msg;
            }

            @Override
            public String addFiles() {
                return msg;
            }

            @Override
            public List<Article> getArticles() {
                return null;
            }

            @Override
            public Article getArticle(Integer id) {
                return null;
            }

            @Override
            public ResponseEntity<Map<String, Object>> saveArticle(Article article, List<Integer> tags) {
                return null;
            }
        });
    }

}
