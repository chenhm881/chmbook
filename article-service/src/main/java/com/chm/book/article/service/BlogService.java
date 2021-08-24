package com.chm.book.article.service;

import com.chm.book.article.domain.TBlog;
import com.chm.book.article.mapper.TBlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheManager = "blogCacheManager")
public class BlogService {

    @Autowired
    private TBlogMapper tBlogMapper;

    public List<TBlog> getTBlog(Integer id) {
        System.out.println("It is run on getTBlog" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        List<TBlog> tBlogList = new ArrayList<>();
        TBlog tBlog = new TBlog();
        tBlog.setId(1L);
        tBlog.setContent("successfully");
        tBlog.setId(1L);
        tBlog.setContent("successfully");
        tBlogList.add(tBlog);
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int ret = 4/1;
        //List<TBlog> tBlogList = tBlogMapper.select();
        return tBlogList;
    }

    @Cacheable(cacheNames = "tBlog", key="#id")
    public TBlog getCacheTBlog(Integer id) {
        System.out.println("It is run on getTBlog");
        TBlog tBlog = tBlogMapper.selectById(id);
        return tBlog;
    }
}
