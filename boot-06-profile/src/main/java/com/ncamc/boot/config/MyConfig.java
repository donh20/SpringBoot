package com.ncamc.boot.config;

import com.ncamc.boot.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MyConfig {

    //在生产环境中往容器放一个color
    @Profile("prod")
    @Bean
    public Color red(){
        return new Color("red");
    }

    //在测试环境中往容器放一个green
    @Profile("uat")
    @Bean
    public Color green(){
        return new Color("green");
    }
}
