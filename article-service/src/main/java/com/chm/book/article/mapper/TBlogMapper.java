package com.chm.book.article.mapper;

import com.chm.book.article.domain.TBlog;

public interface TBlogMapper {
    int insert(TBlog record);

    int insertSelective(TBlog record);
}