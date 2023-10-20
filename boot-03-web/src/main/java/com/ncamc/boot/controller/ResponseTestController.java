package com.ncamc.boot.controller;

import com.ncamc.boot.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.util.Date;

@Controller
public class ResponseTestController {

    /**
     * 1. 浏览器发送请求则直接返回 xml application/xml jacksonXmlConverter
     * 2. ajax请求返回json application/json         jacksonJsonConverter
     * 3. 可以自定义协议 ncamc application/x-ncamc-ia (x表示扩展协议) ncamcDataConverter
     *
     * 步骤：
     * 添加自定义的MessageConverter进系统底层
     * 系统底层自动统计出所有MessageConverter能操作哪些类型
     * 进行客户端和服务器的内容自动协商【x-ncamc-ia->ncamcDataConverter】
     * 打开WebMVCAutoConfiguration
     * org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#configureMessageConverters(java.util.List)
     * 把getConverters获取的内容全都加载到容器里
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/test/person")
    public Person getPerson(){
        Person person = new Person();
        person.setUserName("zhangsan");
        person.setAge(18);

        person.setBirth(new Date());

        return person;
    }
}
