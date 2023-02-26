package com.ncamc.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/bug.jpg")
    public String Hello(){
        return "aaaa";
    }
}
