package com.chm.book.files.domain.audiopiece;

import lombok.Data;

import java.util.List;

@Data
public class ResultBody {
    private String UserAgent;
    private String MediaType;
    private String MediaStorageType;
    private String OriginalFileName;
    private String Blind;
    private List<BodySegment> Segments;
    private String TranscriptionType;
}
