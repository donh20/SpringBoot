package com.ncamc.boot.controller;

import com.ncamc.boot.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class ResponseTestController {

    /**
     * 1. 浏览器发送请求则直接返回 xml application/xml
     * 2. ajax请求返回json application/json jacksonJsonConverter
     * 3. 可以自定义协议 ncamc application/ncamc-ia    ncamcDataConverter
     *
     * 步骤：
     * 添加自定义的MessageConverter
     * 统计出所有MessageConverter能操作哪些类型
     * 内容自动协商
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/test/person")
    public Person getPerson(){
        Person person = new Person();
        person.setUserName("zhangsan");
        person.setAge(18);
        person.setBirth(new Date("19800101"));

        return person;
    }
}
