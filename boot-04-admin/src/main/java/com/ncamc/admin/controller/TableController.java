package com.ncamc.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncamc.admin.bean.User;
import com.ncamc.admin.exception.UserTooManyException;
import com.ncamc.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class TableController {
    @Autowired
    UserService userService;

    @GetMapping("/basic_table")
    public String basic_table(){
        return "table/basic_table";
    }


    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             @RequestParam(value = "pn",defaultValue = "1") Integer pn,
                             RedirectAttributes ra){

        userService.removeById(id);
        //重定向属性,添加一个pn
        ra.addAttribute("pn",pn);
        return "redirect:/dynamic_table";
    }
    @GetMapping("/dynamic_table")
    public String dynamic_table(@RequestParam(value="pn",defaultValue = "1") Integer pn, Model model){
        //遍历表格内容
//        List<User> users = Arrays.asList(new User("zhangsan", "123456"),
//                new User("lisi", "123456"),
//                new User("haha", "aaaaa"),
//                new User("hehe", "bbbbb"));
//        model.addAttribute("users", users);
//        //model.addAttribute(users);
//        if(users.size()>3){
//            throw new UserTooManyException();
//        }

        //分页查询
        Page<User> userpage = new Page<>(pn, 5);
        //分页查询结果
        Page<User> users = userService.page(userpage, null);

        //分页查询的结果

        model.addAttribute("users",users);
        return "table/dynamic_table";
    }

    @GetMapping("/responsive_table")
    public String responsive_table(){
        return "table/responsive_table";
    }

    @GetMapping("/editable_table")
    public String editable_table(){
        return "table/editable_table";
    }
}
