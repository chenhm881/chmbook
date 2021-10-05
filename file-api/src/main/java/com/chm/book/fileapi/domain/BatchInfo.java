package com.chm.book.fileapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchInfo {

    // Actual fields
    private String orgName;
    private String teamName;
    private String projectInitiative;
    private String projectName;
    private Integer radarID;
    private String locale;
    private String description;
    private String DRIEmail;
    private String teamEmailList;
    private String desiredDeliveryDate;

    // DB fields
    private String metadata;
    private String batchID;
}
