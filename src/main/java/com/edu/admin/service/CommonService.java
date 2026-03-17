package com.edu.admin.service;


import com.edu.admin.dao.CommonDao;
import com.edu.admin.model.common.Category;
import com.edu.admin.model.common.FileMeta;
import com.edu.admin.model.common.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CommonService {

    @Value("${upload.path}")
    private String path;

    @Autowired
    CommonDao commonDao;

    @Autowired
    FileStorageService  fileStorageService;

    /**
     * 카테고리 조회
     *
     * @return
     */
    public List<Category> selectCategoryList() {
        return commonDao.selectCategoryList();
    }

    /**
     * 공통코드 조회
     *
     * @return
     */
    public List<CommonCode> selectCommonCodeList( HashMap<String, Object> params) {
        return commonDao.selectCommonCodeList(params);
    }

    /**
     * 파일등록
     * https://github.com/jcodec/jcodec
     *https://github.com/makbn/JThumbnail
     * http://jcodec.org/
     * @param file
     * @return
     */
    public FileMeta addFile(String groupKey, MultipartFile file) {

        FileMeta fileMeta = new FileMeta();

        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file");
            }

            // 파일저장
            fileMeta = fileStorageService.storeFile(file);
            fileMeta.setGroupKey(groupKey);

            //DB 등록
            commonDao.insertFileTmp(fileMeta);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return fileMeta;

    }

    /**
     * 파일삭제
     *
     * @param
     * @return
     */
    public boolean deleteFile(FileMeta vo) {
        try {
            //DB 삭제
            commonDao.deleteFileTmp(vo);

            //DB 삭제
            commonDao.deleteFile(vo);

            //MP 파일
            fileStorageService.storeFileDelete(vo.getFileName().concat("." + vo.getExtension()));
            //PNG 삭제
            fileStorageService.storeFileDelete(vo.getFileName().concat(".png"));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return false;
    }

}