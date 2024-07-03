package com.example.springbootfreemarker.domain;

import lombok.Data;

@Data
public class ArticleTag {
    private Integer articleId;
    private Integer tagId;
    private boolean status;
}
