package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleTags {
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

    private List<Integer> tags;

    private Article article;

}
