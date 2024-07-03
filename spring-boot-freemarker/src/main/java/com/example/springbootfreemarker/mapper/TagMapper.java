package com.example.springbootfreemarker.mapper;


import com.example.springbootfreemarker.domain.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagMapper {
    List<TagEntity> findAll();
}
