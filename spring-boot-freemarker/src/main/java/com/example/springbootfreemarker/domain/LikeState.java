package com.example.springbootfreemarker.domain;

import lombok.Data;

@Data
public class LikeState {
    private String id;

    private Integer articleId;

    private Long authorId;

    private Boolean like;
}
