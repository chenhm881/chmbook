package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    List<CategoryEntity> findAll();
}
