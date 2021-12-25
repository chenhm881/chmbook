package com.chm.book.article.service;

import com.chm.book.article.domain.Comment;
import com.chm.book.article.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> getByArticleId(Integer articleId) {
        return commentMapper.selectByArticleId(articleId);
    }

    public List<Comment> getByAuthorId(Integer authorId) {
        return commentMapper.selectByAuthorId(authorId);
    }

    public Integer save(Comment comment) {
        return commentMapper.insertOrUpdate(comment);
    }

    public Integer delete(Integer id) {
        return commentMapper.delete(id);
    }

}
