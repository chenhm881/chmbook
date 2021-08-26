package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleEntity {
    private Integer id;

    private Integer commentCounts;

    private Date createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer likeCount;

    private Integer weight;

    private Integer status;

    private Long authorId;

    private Integer categoryId;

    private String content;

    private String contentHtml;

}
