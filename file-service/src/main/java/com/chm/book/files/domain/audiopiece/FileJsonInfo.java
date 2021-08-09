package com.chm.book.files.domain.audiopiece;

import lombok.Data;

import java.util.List;

@Data
public class FileJsonInfo {
    private String CompareKey;
    private String Locale;
    private String CorpusName;
    private String DateTimeUtteredUtc;
    private String fileId;
    private List<InfoResult> Result;
    private String jsonString;
}
