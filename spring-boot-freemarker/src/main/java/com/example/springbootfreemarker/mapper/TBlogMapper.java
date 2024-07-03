package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.TBlog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TBlogMapper {
    int insert(TBlog record);
    int insertSelective(TBlog record);
    int insertList(List<TBlog> tBlogList);
    TBlog selectById(Integer id);
    List<TBlog> select();
    int update(Integer id);
    int updateList(List<TBlog> tBlogList);
    int delete(Integer id);
}
