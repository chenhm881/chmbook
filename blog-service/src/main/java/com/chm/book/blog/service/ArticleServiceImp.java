package com.chm.book.blog.service;

import com.chm.book.blog.domain.*;
import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        return new ArticleService() {

            @Override
            public List<ArticleEntity> getArticles() {
                return null;
            }

            @Override
            public List<ArticleEntity> getArticles(ArticleRequest articleRequest) {
                return null;
            }

            @Override
            public List<ArticleEntity> getUserArticles(String authorization, Integer id) {
                return null;
            }

            @Override
            public List<ArticleEntity> getUserArticles(String authorization, ArticleRequest articleRequest) {
                return null;
            }

            @Override
            public List<Comment> getArticleComments(Integer articleId) {
                return null;
            }

            @Override
            public Integer saveComment(String authorization, Comment comment) {
                return null;
            }


            @Override
            public LikeState getOneLike(String authorization, Integer articleId, Integer authorId) {
                return null;
            }

            @Override
            public Integer saveLike(String authorization, LikeState likeState) {
                return null;
            }


            @Override
            public Article getArticle(Integer id) {
                return null;
            }

            @Override
            public Integer save(String authorization, ArticleEntity articleEntity, List<Integer> tags) {
                if (FeignException.Unauthorized.class == throwable.getClass()) {
                    FeignException.Unauthorized feignException=  (FeignException.Unauthorized) throwable;
                    feignException.status();
                }
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("status", HttpStatus.UNAUTHORIZED.value());
                responseMap.put("data", throwable.getMessage());
                ResponseEntity<Map<String,Object>> responseEntity = new ResponseEntity<>(responseMap, HttpStatus.UNAUTHORIZED);
                return 0;
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
