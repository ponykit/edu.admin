package com.edu.admin.dao;

import com.edu.admin.model.common.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CommonDao {

    //공통코드
    List<CommonCode> selectCommonCodeList(HashMap<String, Object> params);

    //카테고리
    List<Category> selectCategoryList();

    //임시파일 등록
    int insertFileTmp(FileMeta file);

    //임시파일 삭제
    int deleteFileTmp(FileMeta file);

    // 파일 삭제
    int deleteFile(FileMeta file);

}