package com.example.springbootfreemarker.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleRequest {
    private Integer id;

    private String authorization;

    private Integer commentCounts;

    private Date createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer likeCounts;

    private Integer weight;

    private Integer status;

    private Long authorId;

    private Integer categoryId;

    private Integer tagId;

}
