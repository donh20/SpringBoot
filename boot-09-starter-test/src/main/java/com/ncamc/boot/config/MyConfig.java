package com.ncamc.boot.config;

import com.ncamc.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    /**
     * 这里启动的话会优先调用，而不是调用starter里的HelloService
     * @return
     */
//    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        return helloService;
    }
}
