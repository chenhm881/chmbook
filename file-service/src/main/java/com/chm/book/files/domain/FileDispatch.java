package com.chm.book.files.domain;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class FileDispatch {
    private File file;
    private List<String> dir;
    private String path;
    private Integer categoryId;
}
