package com.edu.admin.controller;

import com.edu.admin.model.common.*;
import com.edu.admin.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "base")
public class BaseController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/category-mng")
    public String categoryMng(Model model) {
        //카테고리
        List<Category> cateList = commonService.selectCategoryList();
        //태그정보
        HashMap<String, Object> params = new HashMap<>();
        params.put("codeType", "BADGE");
        List<CommonCode> tagList = commonService.selectCommonCodeList(params);
        model.addAttribute("categories", cateList);
        model.addAttribute("tags", tagList);
      return "pages/base/category-mng";
    }

}
