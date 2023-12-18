package com.ncamc.admin.controller;

import com.ncamc.admin.bean.User;
import com.ncamc.admin.exception.UserTooManyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class TableController {
    @GetMapping("/basic_table")
    public String basic_table(){
        return "table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamic_table(Model model){
        //遍历表格内容
        List<User> users = Arrays.asList(new User("zhangsan", "123456"),
                new User("lisi", "123456"),
                new User("haha", "aaaaa"),
                new User("hehe", "bbbbb"));
        model.addAttribute("users", users);
        //model.addAttribute(users);
        if(users.size()>3){
            throw new UserTooManyException();
        }
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
