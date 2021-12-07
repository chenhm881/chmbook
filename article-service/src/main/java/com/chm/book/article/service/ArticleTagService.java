package com.chm.book.article.service;

import com.chm.book.article.domain.ArticleTag;
import com.chm.book.article.mapper.ArticleTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            ArticleTag findArticleTag = articleTagList.stream().filter(item -> item.getTagId().equals(tagId)).findFirst().orElse(null);
            if (findArticleTag != null) {
                for (ArticleTag articleTag : articleTagList) {
                    if (findArticleTag.getTagId().equals(articleTag.getTagId())) {
                        if (!articleTag.isStatus()) {
                            articleTag.setStatus(true);
                            updateArticleTagList.add(articleTag);
                        }
                    } else if (articleTag.isStatus()) {
                        articleTag.setStatus(false);
                        updateArticleTagList.add(articleTag);
                    }
                }

            } else {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tagId);
                articleTag.setStatus(true);
                insertArticleTagList.add(articleTag);
            }
        }

       if(insertArticleTagList.size() > 0) {
           articleTagMapper.insertList(insertArticleTagList);
       }
       if(updateArticleTagList.size() > 0) {
           articleTagMapper.updateList(updateArticleTagList, articleId);
       }
        return 1;
    }

}
