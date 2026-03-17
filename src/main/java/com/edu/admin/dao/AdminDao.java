package com.edu.admin.dao;

import com.edu.admin.model.common.FileVo;
import com.edu.admin.model.course.*;
import com.edu.admin.model.security.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao {

    /**
     * 강의리스트조회
     *
     * @param params
     * @return
     */
    List<Course> selectCourseList(HashMap<String, Object> params);

    /**
     * 강의정보조회
     *
     * @param params
     * @return
     */
    Course selectCourseInfo(HashMap<String, Object> params);

    /**
     * 강의정보조회 상세조회
     *
     * @param params
     * @return
     */
    List<CourseDetail> selectCourseDetailInfo(HashMap<String, Object> params);

    /**
     * 강의정보등록
     *
     * @param param
     * @return
     */
    int mergeCourseInfo(Course param);

    /**
     * 강의연결 파일조회
     * @param params
     * @return
     */
    List<FileVo> selectCoursetFiles(HashMap<String, Object> params);

    /**
     * 강의상세등록
     *
     * @param param
     * @return
     */
    int mergeCourseDetailInfo(CourseDetail param);

    /**
     * 파일정보 이관
     *
     * @param map
     * @return
     */
    int insertFilesTempToFiles(HashMap map);

    /**
     * 관리자 로그인
     *
     * @param param
     * @return
     */
    UserDto selectAdmin(Map<String, Object> param);

    /**
     * 관리자 등록
     *
     * @param param
     * @return
     */
    int addAdminUser(Map<String, Object> param);

    /**
     * 게시판조회
     *
     * @param param
     * @return
     */
    List<HashMap> selectBBSList(Map<String, Object> param);

    /**
     * 게시판상세 조회
     *
     * @param param
     * @return
     */
    HashMap selectBBSDetail(Map<String, Object> param);

    /**
     * 게시판등록
     *
     * @param param
     * @return
     */
    int insertBBS(Map<String, Object> param);

    /**
     * 게시판수정
     *
     * @param param
     * @return
     */
    int updateBBS(Map<String, Object> param);

}