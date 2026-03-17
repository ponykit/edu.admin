package com.edu.admin.model.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

@Getter
@Setter
public  class FileMeta {
   int fileSeq;
   String groupKey;
   String fileName;
   long fileSize;
   String mimeType;
   String fileUrl;
   String thumbnailUrl;
   String extension;


}
