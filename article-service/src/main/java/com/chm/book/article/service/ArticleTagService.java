package com.chm.book.article.service;

import com.chm.book.article.domain.ArticleTag;
import com.chm.book.article.mapper.ArticleTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    public List<ArticleTag> findAll(Integer articleId) {
        return articleTagMapper.findAll(articleId);
    }

    public List<ArticleTag> find(Integer articleId) {
        return articleTagMapper.find(articleId);
    }

    public int insert(Integer articleId, List<Integer> tags) {
        List<ArticleTag> articleTagList = findAll(articleId);

        List<ArticleTag> insertArticleTagList = new ArrayList<>();
        List<ArticleTag> noUpdateArticleTagList = new ArrayList<>();
        List<ArticleTag> addUpdateArticleTagList = new ArrayList<>();
        List<Integer> insertTagList = new ArrayList<>();

        for(Integer tagId : tags) {
            boolean isTag = false;
            for (ArticleTag articleTag : articleTagList) {
                if (articleTag.getTagId().equals(tagId)) {
                    isTag = true;
                    noUpdateArticleTagList.add(articleTag);
                    if (!articleTag.isStatus()) {
                        articleTag.setStatus(true);
                        addUpdateArticleTagList.add(articleTag);
                    }
                }
            }
            if (!isTag) {
                insertTagList.add(tagId);
            }
        }

        articleTagList.removeAll(noUpdateArticleTagList);

        articleTagList = articleTagList.stream().filter(articleTag -> articleTag.isStatus()).map(articleTag -> {
            articleTag.setStatus(false);
            return articleTag;
        }).collect(Collectors.toList());

        articleTagList.addAll(addUpdateArticleTagList);

        insertTagList.forEach(tagId -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tagId);
            articleTag.setStatus(true);
            insertArticleTagList.add(articleTag);
        });

        if(insertArticleTagList.size() > 0) {
           articleTagMapper.insertList(insertArticleTagList);
        }
        if(articleTagList.size() > 0) {
           articleTagMapper.updateList(articleTagList, articleId);
        }
        return 1;
    }

}
