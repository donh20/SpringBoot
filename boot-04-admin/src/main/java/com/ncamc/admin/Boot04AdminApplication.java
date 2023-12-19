package com.ncamc.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.ncamc.admin")
@SpringBootApplication
public class Boot04AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot04AdminApplication.class, args);
    }

}
