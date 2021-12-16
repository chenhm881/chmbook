package com.chm.book.blog.entityservice;

import com.chm.book.blog.domain.ArticleEntity;
import com.chm.book.blog.domain.ArticleTags;
import com.chm.book.blog.domain.SysUser;
import com.chm.book.blog.mapper.UserMapper;
import com.chm.book.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleEntityService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public ArticleEntity createEntity(String authorization, ArticleTags articleTags) {
        SysUser sysUser = findAuthenticationUser(authorization);

        ArticleEntity entity = new ArticleEntity();
        entity.setId(articleTags.getId());
        entity.setTitle(articleTags.getTitle());
        entity.setAuthorId(articleTags.getAuthorId());
        entity.setCategoryId(articleTags.getCategoryId());
        entity.setLikeCount(articleTags.getLikeCount());
        entity.setCreateDate(articleTags.getCreateDate());
        entity.setSummary(articleTags.getSummary());
        entity.setContent(articleTags.getContent());
        entity.setContentHtml(articleTags.getContentHtml());
        entity.setStatus(articleTags.getStatus());
        entity.setWeight(articleTags.getWeight());
        entity.setViewCounts(articleTags.getViewCounts());
        entity.setCommentCounts(articleTags.getCommentCounts());
        entity.setAuthorId(sysUser.getId().longValue());
        return entity;
    };


    public SysUser findAuthenticationUser(String authorization) {
        Map<String, Object> resultMap = userService.findUser(authorization);
        String username = resultMap.get("user").toString();
        SysUser sysUser = userMapper.findUserByUsername(username);
        return sysUser;
    }

}
