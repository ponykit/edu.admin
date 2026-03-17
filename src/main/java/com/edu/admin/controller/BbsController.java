/*
 * Interpark Tour Team, INTERPARK INC., SEOUL, KOREA
 * Copyright(c) 2018 by Interpark Inc.
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of Interpark Inc.
 *
 */
package com.edu.admin.controller;

import com.edu.admin.model.common.CommonCode;
import com.edu.admin.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "/board")
public class BbsController {

    CommonService commonService;

    public BbsController(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * 게시판관리 화면이동
     *
     * @param request
     * @param input
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bbs/management")
    public ModelAndView viewBbsManagement(HttpServletRequest request, @RequestParam HashMap<String, Object> input) throws Exception {
        ModelAndView mv = new ModelAndView();
        HashMap<String, Object> comCode = new HashMap<>();
        comCode.put("codeType", "BOCATE");
        List<CommonCode> boCateList = commonService.selectCommonCodeList(comCode);

        mv.addObject("boCateList", boCateList);
        mv.setViewName("pages/board/bbs-management");
        return mv;
    }

}
