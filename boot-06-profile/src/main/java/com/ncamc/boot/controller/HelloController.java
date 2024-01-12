package com.ncamc.boot.controller;


import com.ncamc.boot.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${person.name:李四}")
    private String name;

    @Autowired
    private Person person;

    @GetMapping("/")
    public String hello(){
        return "hello "+name;
    }

    @GetMapping("/person")
    public Person person(){
        return person;
    }

}
