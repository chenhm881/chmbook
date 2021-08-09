package com.chm.book.files.domain.audiopiece;

import lombok.Data;

@Data
public class BodySegment {
    private String SegmentID;
    private float Start;
    private float End;
    private double Confidence;
    private String RecoText;
    private String TranscriptionContent;
}
