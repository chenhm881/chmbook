package com.chm.book.blog.service;

import com.chm.book.blog.domain.Article;
import com.chm.book.blog.domain.ArticleEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(name="ZUUL-GATEWAY-FILE", url="http://ZUUL-GATEWAY")
public interface FileService {

    @PostMapping(value = "/file/api/addFiles")
    public String addFiles();

}


