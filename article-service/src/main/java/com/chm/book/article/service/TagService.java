package com.chm.book.article.service;

import com.chm.book.article.domain.TagEntity;
import com.chm.book.article.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<TagEntity> getTags() {
        return tagMapper.findAll();
    }

}
