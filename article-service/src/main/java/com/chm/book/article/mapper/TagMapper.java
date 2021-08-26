package com.chm.book.article.mapper;

import com.chm.book.article.domain.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagMapper {
    List<TagEntity> findAll();
}
