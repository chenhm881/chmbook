package com.chm.book.article.service;

import com.chm.book.article.domain.TBlog;
import com.chm.book.article.mapper.TBlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@CacheConfig(cacheManager = "blogCacheManager")
public class BlogService {

    @Autowired
    private TBlogMapper tBlogMapper;

    public List<TBlog> getTBlog(Integer id) {
        System.out.println("It is run on getTBlog");
        List<TBlog> tBlogList = tBlogMapper.select();
        return tBlogList;
    }

    @Cacheable(cacheNames = "tBlog", key="#id")
    public TBlog getCacheTBlog(Integer id) {
        System.out.println("It is run on getTBlog");
        TBlog tBlog = tBlogMapper.selectById(id);
        return tBlog;
    }
}
