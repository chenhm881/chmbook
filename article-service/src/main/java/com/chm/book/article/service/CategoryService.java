package com.chm.book.article.service;

import com.chm.book.article.domain.CategoryEntity;
import com.chm.book.article.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryEntity> getCategories() {
        return categoryMapper.findAll();
    }

}
