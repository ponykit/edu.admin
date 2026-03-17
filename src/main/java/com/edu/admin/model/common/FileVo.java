package com.edu.admin.model.common;

import lombok.Data;

import java.io.InputStream;

@Data
public class FileVo {

        private String fileSeq;
        private String courseSeq;
        private String fileName;
        private String fileBase64;
        private String fileSize;
        private String useYn;

}
