package com.edu.admin.model.course;

import lombok.Data;

@Data
public class CourseDetail {
    private int courseDtlSeq;
    private int courseSeq;
    private int fileSeq;
    private String fileName;
    private String extension;
    private String fileUrl;
    private String unitTitle;
    private String thumbnailUrl;
    private String fileSize;
    private String displayYn = "1";
    private String sort;
    private String regDt;
    private String regId;


}

