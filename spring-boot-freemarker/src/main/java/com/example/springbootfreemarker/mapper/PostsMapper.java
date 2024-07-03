package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.Posts;
import com.example.springbootfreemarker.domain.PostsWithBLOBs;

public interface PostsMapper {
    int deleteByPrimaryKey(Integer postsId);

    int insert(PostsWithBLOBs record);

    int insertSelective(PostsWithBLOBs record);

    PostsWithBLOBs selectByPrimaryKey(Integer postsId);

    int updateByPrimaryKeySelective(PostsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostsWithBLOBs record);

    int updateByPrimaryKey(Posts record);
}
