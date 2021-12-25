package com.chm.book.article.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeStateKey {
    private Integer articleId;
    private Long authorId;

}
