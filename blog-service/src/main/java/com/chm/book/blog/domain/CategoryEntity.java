package com.chm.book.blog.domain;

import lombok.Data;

@Data
public class CategoryEntity {
    private Integer categoryId;
    private Integer categoryPid;
    private String categoryName;
    private String categoryDescription;
    private Integer categoryOrder;
    private Integer categoryIcon;
}
