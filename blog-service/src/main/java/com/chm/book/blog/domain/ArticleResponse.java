package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleResponse extends Article {
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

    private SysUser user;

    private CategoryEntity category;

    private List<TagEntity> tags;

}
