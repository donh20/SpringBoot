package com.ncamc.hello.service;

import com.ncamc.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String userName){
        return helloProperties.getPrefix() + ": " +userName+ ">>>>>>>>>" + helloProperties.getSuffix();
    }
}
