package com.edu.admin.controller;

import com.edu.admin.util.StringUtil;
import com.edu.admin.model.ApiResult;
import com.edu.admin.model.course.Course;
import com.edu.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api")
public class RestAdminController {

    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/course/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult courseList(@RequestBody HashMap<String, Object> params) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {

            //페이징 세팅
            List<Course>  rslt = adminService.selectCourseList(params);
            int current = StringUtil.parseInt(params.get("Current"));
            int rowCount = StringUtil.parseInt(params.get("RowCount"));

            result.setCurrent(current);
            result.setRowCount(rowCount);
            result.setTotal(rslt.size() > 0  ? rslt.get(0).getTotal() : 0);
            result.setRows(rslt);
        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/course/insertUpdate", method = RequestMethod.POST)
    public ApiResult courseReg(@RequestBody Course course) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {

            adminService.mergeCourseInfo(course);
            log.debug("Course saved: {}", course);
        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/bbs/management/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult bbsList(@RequestBody HashMap<String, Object> params) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {

            //페이징 세팅
            List<HashMap>   rslt = adminService.selectBBSList(params);
            int current = StringUtil.parseInt(params.get("Current"));
            int rowCount = StringUtil.parseInt(params.get("RowCount"));

            result.setCurrent(current);
            result.setRowCount(rowCount);
            result.setTotal(rslt.size() > 0  ? Long.parseLong(rslt.get(0).get("BoRowCnt").toString()) : 0);
            result.setRows(rslt);
        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/bbs/management/detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult bbsDetail(@RequestBody HashMap<String, Object> params) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {
            //페이징 세팅
            HashMap  rslt = adminService.selectBBSDetail(params);
            result.setRows(rslt);
        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/bbs/management/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult bbsInsert(HttpServletRequest request, @RequestBody HashMap<String, Object> params) throws Exception {
        ApiResult result = new ApiResult("200", "success");
        try {
            HttpSession session = request.getSession();

            int  rslt = 0;
            if(params.get("BoNum") != null) {
                rslt = adminService.updateBBS(params);
            }
            else {
                rslt = adminService.insertBBS(params);
            }


            result.setRows(rslt);
        } catch (Exception e) {
            result.setCode("500");
            result.setMessage(e.getMessage());
        }

        return result;
    }


}