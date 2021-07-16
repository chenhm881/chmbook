package com.chm.book.article.mapper;

import com.chm.book.article.domain.Posts;
import com.chm.book.article.domain.PostsWithBLOBs;

public interface PostsMapper {
    int deleteByPrimaryKey(Integer postsId);

    int insert(PostsWithBLOBs record);

    int insertSelective(PostsWithBLOBs record);

    PostsWithBLOBs selectByPrimaryKey(Integer postsId);

    int updateByPrimaryKeySelective(PostsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostsWithBLOBs record);

    int updateByPrimaryKey(Posts record);
}