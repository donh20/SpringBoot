package com.ncamc.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
2. 请求解析测试
测试取出请求域中的数据,必须要转发请求,而不能重定向
转发才能共享response域和request域,重定向不行
 */

@Controller
public class RequestController {

    @GetMapping("/goto")
    public String goTo(HttpServletRequest request){
        request.setAttribute("msg", "成功了...");
        request.setAttribute("code", 200);

        return "forward:/success"; //转发到success请求
    }

    @GetMapping("/params")
    public String testParam(Map<String ,Object> map,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response){
        map.put("hello", "hello");
        model.addAttribute("world", "world" );
        request.setAttribute("message","message");
        Cookie cookie = new Cookie("cookie", "value");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return "forward:/success";
    }

    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute(value = "msg",required = false) String msg,
                       @RequestAttribute(value = "code",required = false) Integer code,
                       HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();

        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");


        map.put("request_method_msg", request.getAttribute("msg"));
        map.put("annotation_msg", msg);

        map.put("hello",hello);
        map.put("world",world);
        map.put("message",message);
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
