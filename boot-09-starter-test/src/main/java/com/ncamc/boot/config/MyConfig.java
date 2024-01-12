package com.ncamc.boot.config;

import com.ncamc.hello.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    /**
     * 如果放一个helloService类的话，启动的时候会覆盖starter里的HelloService类
     * @return
     */
//    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        return helloService;
    }
}
