package com.chm.book.files.domain;

import lombok.Data;

import java.util.List;

@Data
public class FileRawJson {
    private String CompareKey;
    private String Locale;
    private String CorpusName;
    private String DateTimeUtteredUtc;
    private String fileId;
    private String jsonString;
}
