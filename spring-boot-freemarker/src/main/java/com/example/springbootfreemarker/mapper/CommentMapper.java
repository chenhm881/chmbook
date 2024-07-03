package com.example.springbootfreemarker.mapper;

import com.example.springbootfreemarker.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int delete(Integer id);

    int insert(Comment comment);

    int insertOrUpdate(Comment comment);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectByArticleId(Integer articleId);

    List<Comment> selectByAuthorId(Integer authorId);

    int updateByPrimaryKey(Comment comment);
}
