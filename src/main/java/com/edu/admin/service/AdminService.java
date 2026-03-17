package com.edu.admin.service;

import com.edu.admin.dao.AdminDao;
import com.edu.admin.model.common.FileVo;
import com.edu.admin.model.course.*;
import com.edu.admin.util.StringUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    FileStorageService  fileStorageService;


    /**
     * 강의리스트 조회
     *
     * @return
     */
    public  List<Course> selectCourseList(HashMap<String, Object> params) {
        List<Course> rslt  = adminDao.selectCourseList(params);

        return rslt;
    }

    /**
     * 강의리스트 상세
     *
     * @return
     */
    public  Course selectCourseDetail(HashMap<String, Object> params) {
        Course courseInfo = adminDao.selectCourseInfo(params);
        List<CourseDetail> courseDetail = adminDao.selectCourseDetailInfo(params);

        courseInfo.setCourseDetail(courseDetail);
        return courseInfo;
    }

    /**
     * 강의연결파일 조회
     *
     * @return
     */
    public  List<FileVo> selectCoursetFiles(HashMap<String, Object> params) {
        List<FileVo> rslt  = adminDao.selectCoursetFiles(params);

        return rslt;
    }

    /**
     * 강의정보 등록
     *
     * @return
     */
    public int mergeCourseInfo(Course param) {
        int rslt = 0;

        //메인이미지 파일저장
        if(StringUtil.isNotEmpty(param.getMainImg() )) {
            param.setMainImg(fileStorageService.storeBase64File(param.getMainImg()));
        }

        //기본정보 등록
        rslt = adminDao.mergeCourseInfo(param);

        //강의상세 정보 등록
        if(param.getCourseDetail() != null) {
            for (CourseDetail item : param.getCourseDetail()) {
                item.setCourseSeq(param.getCourseSeq());
                adminDao.mergeCourseDetailInfo(item);
            }
        }

        //임시 파일 이관
        HashMap mParam =  new HashMap();
        mParam.put("courseSeq", param.getCourseSeq());
        mParam.put("groupKey", param.getGroupKey());
        adminDao.insertFilesTempToFiles(mParam);

        return rslt;
    }

    /**
     * 게시판 조회
     *
     * @return
     */
    public  List<HashMap> selectBBSList(HashMap<String, Object> params) {

        List<HashMap> rslt  = adminDao.selectBBSList(params);

        return rslt;
    }

    /**
     * 게시판상세 조회
     *
     * @return
     */
    public  HashMap selectBBSDetail(HashMap<String, Object> params) {
        return   adminDao.selectBBSDetail(params);
    }

    /**
     * 게시판등록
     *
     * @return
     */
    public  int insertBBS(HashMap<String, Object> params) {
        return  adminDao.insertBBS(params);
    }

    /**
     * 게시판수정
     *
     * @return
     */
    public  int updateBBS(HashMap<String, Object> params) {
        params.put("BoNum", Integer.parseInt(params.get("BoNum").toString()));
        return  adminDao.updateBBS(params);
    }
}
