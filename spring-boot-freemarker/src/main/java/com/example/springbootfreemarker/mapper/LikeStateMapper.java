package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.LikeState;
import com.example.springbootfreemarker.domain.LikeStateKey;
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
