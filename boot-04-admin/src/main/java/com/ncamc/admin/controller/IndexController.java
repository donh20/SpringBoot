package com.ncamc.admin.controller;

import com.ncamc.admin.bean.Accounts;
import com.ncamc.admin.bean.City;
import com.ncamc.admin.bean.User;
import com.ncamc.admin.service.AccountService;
import com.ncamc.admin.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountService accountService;

    @Autowired
    CityService cityService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @PostMapping("/city")
    public City saveCity(City city){
        cityService.saveCity(city);
        return city;
    }
    @ResponseBody
    @GetMapping("/city")
    public City getCityById(@RequestParam("id") Long id){
        return cityService.getById(id);
    }
    @ResponseBody
    @GetMapping("/acct")
    public Accounts getById(Long id){
        return accountService.getAcctById(id);
    }
    @ResponseBody
    @GetMapping("/sql")
    public String queryFromDB() {
        Long aLong = jdbcTemplate.queryForObject("select count(*) from project", Long.class);
        log.info("记录总数{}",aLong);
        return aLong.toString();
    }

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
    public String mainPage(Model model){

        log.info("当前方法是:{}","mainPage");
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String index = opsForValue.get("/index");
        String sql = opsForValue.get("/sql");

        model.addAttribute("index",index);
        model.addAttribute("sql",sql);

        return "index";
    }
}
