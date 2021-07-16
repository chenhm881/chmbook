package com.chm.book.blog.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

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
            public String select() {
                return msg;
            }
        });
    }
}
