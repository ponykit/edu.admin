package com.edu.admin.model.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* 페이징 처리 공통 클래스
 */
@Getter
@Setter
public class BasePaging {

    private int rowCount;
    private int current;
    private Long total;

}
