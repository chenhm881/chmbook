package com.chm.book.blog.domain;

import lombok.Data;

@Data
public class TagEntity {
    private Integer id;
    private String tagName;
    private String tagDescription;
}
