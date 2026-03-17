package com.edu.admin.controller;

import com.edu.admin.model.ApiResult;
import com.edu.admin.model.common.FileMeta;
import com.edu.admin.model.common.FileVo;
import com.edu.admin.model.course.Course;
import com.edu.admin.model.course.CourseDetail;
import com.edu.admin.service.CommonService;
import com.edu.admin.service.FileStorageService;
import com.edu.admin.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("common")
public class RestCommonController {

    @Autowired
    CommonService commonService;
    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map file_upload(
            @RequestParam(value = "groupKey") String groupKey,
            @RequestParam(value = "files[]", required = false) MultipartFile[] files) throws IllegalStateException, IOException {

        List<FileMeta> fileMetas = new ArrayList<>();
        for (MultipartFile file : files) {
            FileMeta fileMeta = commonService.addFile(groupKey, file);
            fileMetas.add(fileMeta);
        }

        Map<String, Object> rslt = new HashMap<>();
        rslt.put("files", fileMetas);
        return rslt;
    }

    @ResponseBody
    @RequestMapping(value = "/file/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ApiResult file_delete(@RequestBody Course course) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {

            for (CourseDetail item: course.getCourseDetail()) {
                FileMeta fileMeta = new FileMeta();
                fileMeta.setFileSeq(item.getFileSeq());
                fileMeta.setFileName(item.getFileName());
                fileMeta.setExtension(item.getExtension());
                commonService.deleteFile(fileMeta);
            }

        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping(value= {"/filepreview/{fileName:.+}", "/img/{fileName:.+}"})
    public ResponseEntity<Resource> file_preview(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/filepath/{fileName:.+}")
    public ResponseEntity<Resource> file_download(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}