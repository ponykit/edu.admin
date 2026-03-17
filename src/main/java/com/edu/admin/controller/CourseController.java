package com.edu.admin.controller;

import com.edu.admin.model.common.Category;
import com.edu.admin.model.common.CommonCode;
import com.edu.admin.model.common.FileVo;
import com.edu.admin.model.course.Course;
import com.edu.admin.service.AdminService;
import com.edu.admin.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "courses")
public class CourseController {
    @Autowired
    CommonService commonService;

    @Autowired
    AdminService adminService;

    @RequestMapping("/courses-list")
    public String coursesList(Model model) {

        List<Category> cateList = commonService.selectCategoryList();

        HashMap<String, Object> params = new HashMap<>();
        params.put("codeType", "BADGE");
        List<CommonCode> codeList = commonService.selectCommonCodeList(params);

        model.addAttribute("categories", cateList);
        model.addAttribute("tags", codeList);

      return "pages/courses/course-list";
    }

    @RequestMapping("/courses-detail")
    public String coursesDetail(Model model, @RequestParam HashMap<String, Object> params) {
        List<Category> cateList = commonService.selectCategoryList();
        HashMap<String, Object> comCode = new HashMap<>();
        comCode.put("codeType", "BADGE");
        List<CommonCode> badge = commonService.selectCommonCodeList(comCode);
        comCode.put("codeType", "ONOFFTYPE");
        List<CommonCode> onofftype = commonService.selectCommonCodeList(comCode);

        Course courseDetail =  adminService.selectCourseDetail(params);

        model.addAttribute("categories", cateList);
        model.addAttribute("tags", badge);
        model.addAttribute("onofftype", onofftype);
        model.addAttribute("courseDetail", courseDetail);

        return "pages/courses/course-detail";
    }

    @RequestMapping("/courses-reg")
    public String coursesReg(Model model) {
        List<Category> cateList = commonService.selectCategoryList();

        HashMap<String, Object> comCode = new HashMap<>();
        comCode.put("codeType", "BADGE");
        List<CommonCode> codeList = commonService.selectCommonCodeList(comCode);

        model.addAttribute("categories", cateList);
        model.addAttribute("tags", codeList);
        model.addAttribute("courseDetail", new Course());

        return "pages/courses/course-detail";
    }
}
