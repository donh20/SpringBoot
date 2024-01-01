package com.ncamc.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.ncamc.admin.mapper")
@ServletComponentScan(basePackages = "com.ncamc.admin")
@SpringBootApplication
public class Boot04AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot04AdminApplication.class, args);
    }

}
