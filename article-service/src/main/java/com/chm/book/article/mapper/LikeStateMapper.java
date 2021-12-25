package com.chm.book.article.mapper;

import com.chm.book.article.domain.LikeState;
import com.chm.book.article.domain.LikeStateKey;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeStateMapper {
    int deleteByPrimaryKey(LikeStateKey key);

    int delete(Integer articleId, Integer authorId);

    int insert(LikeState likeState);

    int insertSelective(LikeState likeState);

    LikeState selectByPrimaryKey(LikeStateKey key);

    int insertOrUpdate(LikeState likeState);

    int updateByPrimaryKey(LikeState record);
}
