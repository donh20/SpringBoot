package com.ncamc.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewTestController {
    @GetMapping("/ncamc")
    public String ncamc(Model model){
        model.addAttribute("msg", "hello world");
        model.addAttribute("link","http://www.baidu.com");
        return "success";
    }
}
