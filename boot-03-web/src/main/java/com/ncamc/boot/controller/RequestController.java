package com.ncamc.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试取出请求域中的数据,必须要转发请求,而不能重定向
 * 转发才能共享response域和request域,重定向不行
 */
@Controller
public class RequestController {

    @GetMapping("/goto")
    public String goTo(HttpServletRequest request){
        request.setAttribute("msg", "成功了...");
        request.setAttribute("code", 200);

        return "forward:/success";
    }

    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg") String msg,
                       @RequestAttribute("code") Integer code,
                       HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("request_method_msg", request.getAttribute("msg"));
        map.put("annotation_msg", msg);
        return map;
    }

    /**
     *      语法: "/cars/sell;low=34;brand=byd,audi,yd"
     *      SpringBoot默认禁用了矩阵变量的功能,要手动开启
     *      对于整个路径的处理都是通过UrlPathHelper来解析的
     *      其中有一个属性removeSemicolonContent(要不要移除分号后的内容)是用来支持矩阵变量的
     *      想要自定义,SpringBoot提供了三种方案:
     *      1. 不用@EnableWebMvc注解,使用@Configuration,WebMvcConfigurer自定义规则(但是不能加EnableWebMvc注解)
     *      2. 声明WebMvcRegistrations改变默认底层组件
     *      3. 使用@EnableWebMvc+@Configuration+DelegatingWebMvcConfiguration全面接管SpringMVC
     * @param low
     * @param brand
     * @return
     */


    @ResponseBody
    @GetMapping("/cars/{path}")
    public Map carsSall(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path){
        HashMap<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }
    // /boss/1;age=20/2;age=10
    @ResponseBody
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empId") Integer empAge){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
