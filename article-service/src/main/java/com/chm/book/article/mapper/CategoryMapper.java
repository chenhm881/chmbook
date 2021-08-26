package com.chm.book.article.mapper;

import com.chm.book.article.domain.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<CategoryEntity> findAll();
}
