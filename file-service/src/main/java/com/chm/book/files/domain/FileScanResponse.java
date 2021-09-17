package com.chm.book.files.domain;

import com.chm.book.files.domain.audiopiece.InfoResult;
import lombok.Data;

import java.util.List;

@Data
public class FileScanResponse {
    private String CompareKey;
    private String Locale;
    private String CorpusName;
    private String DateTimeUtteredUtc;
    private String fileId;
    private List<InfoResult> Result;
    private String jsonString;
}


