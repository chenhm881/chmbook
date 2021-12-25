package com.chm.book.article.service;

import com.chm.book.article.domain.Comment;
import com.chm.book.article.domain.LikeState;
import com.chm.book.article.domain.LikeStateKey;
import com.chm.book.article.mapper.CommentMapper;
import com.chm.book.article.mapper.LikeStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeStateService {

    @Autowired
    private LikeStateMapper likeStateMapper;

    public Integer delete(Integer articleId, Integer authorId) {
        return likeStateMapper.delete(articleId, authorId);
    }

    public Integer save(LikeState likeState) {
        return likeStateMapper.insertOrUpdate(likeState);
    }


    public LikeState find(LikeStateKey likeStateKey) {
        return likeStateMapper.selectByPrimaryKey(likeStateKey);
    }
}
