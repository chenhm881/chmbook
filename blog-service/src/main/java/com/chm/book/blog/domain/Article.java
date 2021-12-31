package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    private Integer id;

    private Integer commentCounts;

    private Date createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer likeCounts;

    private Integer weight;

    private Integer status;

    private Long authorId;

    private String content;

    private String contentHtml;

    private CategoryEntity category;

    private List<TagEntity>  tags;

}
