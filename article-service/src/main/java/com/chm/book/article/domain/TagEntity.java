package com.chm.book.article.domain;

import lombok.Data;

@Data
public class TagEntity {
    private Integer id;
    private String tagName;
    private String tagDescription;
}
