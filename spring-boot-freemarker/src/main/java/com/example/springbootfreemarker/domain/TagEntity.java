package com.example.springbootfreemarker.domain;

import lombok.Data;

@Data
public class TagEntity {
    private Integer id;
    private String tagName;
    private String tagDescription;
}
