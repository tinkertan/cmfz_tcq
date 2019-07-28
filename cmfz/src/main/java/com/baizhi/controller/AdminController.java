package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        //使用shiro
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(admin.getUsername(),admin.getPassword()));
            return "redirect:/jsp/main.jsp";
        }catch (UnknownAccountException e){
            modelMap.addAttribute("message","账号不存在");
            return "/jsp/login";
        }catch (IncorrectCredentialsException e1){
            modelMap.addAttribute("message","密码不正确");
            return "/jsp/login";
        }
        //未使用shiro
        /*try{
            Admin admin1 = adminService.login(admin);
            session.setAttribute("admin",admin1);
            return "redirect:/jsp/main.jsp";
        }catch(Exception e){
            e.printStackTrace();
            modelMap.addAttribute("message",e.getMessage());
            return "/jsp/login";
        }*/
    }
    @RequestMapping("logout")
    public String logout(HttpSession session){
        /*session.removeAttribute("admin");*/
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/jsp/login.jsp";
    }
}
