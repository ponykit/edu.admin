package com.edu.admin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(HttpSession session, @AuthenticationPrincipal User user) {
        if(user != null) {
            return "redirect:/home/blank";
        }

        return "redirect:/login";
    }

    @GetMapping("/home/blank")
    public String main(Model model) {

      return "base";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        return "pages/dashboard";
    }

    /**
     * 로그인
     * @return
     */
    @GetMapping(value = "/login")
    public String login(Model model) throws Exception {
        return "login";
    }
}
