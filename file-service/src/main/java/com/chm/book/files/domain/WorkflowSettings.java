package com.chm.book.files.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkflowSettings {
    @JsonIgnore
    private String id;
    private Integer categoryId;
    private Integer workflowId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private Boolean enable;
    private String days = "";
    private String hours;
    private String matterInfo;
    private String triggeredBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime updatedDate;

}
