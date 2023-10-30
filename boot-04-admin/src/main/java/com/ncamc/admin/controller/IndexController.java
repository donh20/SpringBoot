package com.ncamc.admin.controller;

import com.ncamc.admin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/login"})
    public String loginPage(){
        return "login";
    }

    /**
     * 接收post请求，登录请求会跳转到这里
     * @return
     */
    @PostMapping("/login")
    public String main(User user, HttpSession httpSession, Model model){
        if(StringUtils.hasLength(user.getUserName())&&"123456".equals(user.getPassword())) {
            //登录成功的用户保存起来
            httpSession.setAttribute("loginUser", user);
        } else {
            model.addAttribute("msg","账号密码错误");
            return "login";
        }


        //这么写的话，请求还是login，页面是转发到index页面
        //return "index";

        //登录成功，重定向到index.html
        return "redirect:/index";
    }

    /**
     * 接收对login页面刷新的get请求，登录后的刷新请求会调转到这里
     * @return
     */
    @GetMapping("/index")
    public String mainPage(){

        return "index";
    }
}
