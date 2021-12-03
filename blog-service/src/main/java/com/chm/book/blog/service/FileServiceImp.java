package com.chm.book.blog.service;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FileServiceImp implements FallbackFactory<FileService> {

    Logger logger = LoggerFactory.getLogger(FileServiceImp.class);

    @Override
    public FileService create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            logger.error(msg);
        }
        return (new FileService() {

            @Override
            public String addFiles() {
                return msg;
            }
        });
    }

}
