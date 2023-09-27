package com.ncamc.boot.controller;

import com.ncamc.boot.bean.Person;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数解析测试
 * 几种常见的注解测试
 *
 */
@RestController
public class ParameterTestController {

    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(@PathVariable("id") Integer id,
                                      @PathVariable("username") String username,
                                      @PathVariable Map<String, Object> pv,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader Map<String, Object> header,
                                      @RequestParam("age") Integer age,
                                      @RequestParam("interest") List<String> interests,
                                      @RequestParam Map<String, String> params
                                      //@CookieValue("_ga") Cookie cookie,
                                      )
    {

        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("username", username);
//        map.put("pv", pv);
//        map.put("User-Agent", userAgent);
//        map.put("headers", header);
        map.put("age", age);
        map.put("interests", interests);
        map.put("params", params);
        //System.out.println(cookie);

        return map;
    }

    @PostMapping("/save")
    public Map<String, Object> postMethod(@RequestBody String content){

        HashMap<String, Object> map = new HashMap<>();
        map.put("content", content);
        return map;
    }


    @PostMapping("/saveuser")
    public Person saveuser(Person person){
        return person;
    }
}
