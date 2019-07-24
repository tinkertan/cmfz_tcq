package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public String login(Admin admin, HttpSession session, ModelMap modelMap){
        try{
            Admin admin1 = adminService.login(admin);
            session.setAttribute("admin",admin1);
            return "redirect:/jsp/main.jsp";
        }catch(Exception e){
            e.printStackTrace();
            modelMap.addAttribute("message",e.getMessage());
            return "/jsp/login";
        }
    }
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }


}
