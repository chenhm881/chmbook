package com.chm.book.article.service;

import com.chm.book.article.domain.ArticleTag;
import com.chm.book.article.mapper.ArticleTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<ArticleTag> updateArticleTagList = new ArrayList<>();

        for(Integer tagId : tags) {
            Boolean isFind = false;
            for(ArticleTag articleTag : articleTagList) {
                if (articleTag.getTagId().equals(tagId)) {
                    if (!articleTag.getStatus()) {
                        articleTag.setStatus(true);
                        updateArticleTagList.add(articleTag);
                    }
                    isFind = true;
                    break;
                }
                if (articleTag.getStatus()) {
                    articleTag.setStatus(false);
                    updateArticleTagList.add(articleTag);
                }
            }

            if (!isFind) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tagId);
                articleTag.setStatus(true);
                insertArticleTagList.add(articleTag);
            }
        }

        articleTagMapper.insertList(insertArticleTagList);
        articleTagMapper.updateList(updateArticleTagList);
        return 1;
    }

}
