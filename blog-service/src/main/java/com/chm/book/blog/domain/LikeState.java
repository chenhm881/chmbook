package com.chm.book.blog.domain;

import lombok.Data;

@Data
public class LikeState {
    private String id;

    private Integer articleId;

    private Long authorId;

    private Boolean like;
}
