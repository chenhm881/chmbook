package com.chm.book.blog.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;

    private Integer parentId;

    private Integer articleId;

    private Long authorId;

    private String authorName;

    private String content;

    private Date createDate;

    private String level;

    private Long toUid;

}
