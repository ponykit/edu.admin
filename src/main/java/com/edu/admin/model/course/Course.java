package com.edu.admin.model.course;

import com.edu.admin.model.common.BasePaging;
import lombok.Data;

import java.util.List;

@Data
public class Course extends BasePaging {
    private String groupKey;
    private int courseSeq;
    private String title;
    private String categoryId;
    private String categoryName;
    private String mainImg;
    private String price;
    private String badge = "";
    private String introd = "";
    private String displayYn = "1";
    private String contentDetail = "";
    private String regDt;
    private String regId;
    private List<CourseDetail> courseDetail;

}
