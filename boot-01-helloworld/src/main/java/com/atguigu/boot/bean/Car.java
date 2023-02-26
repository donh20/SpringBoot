package com.atguigu.boot.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 频繁变动属性的类可以通过ConfigurationProperties注解进行配置绑定
 *
 * 配置绑定的两种方式:
 * 1. @Component + @ConfigurationProperties
 *  为什么要加component?
 *  因为类的注入,要么用@ComponentScan,要么在config类里用@Bean注入.
 *  car没有在config里用@Bean注入,这个时候需要加扫描
 *
 * 2. @EnableConfigurationProperties + @ConfigurationProperties
 * 如果不加@Component,则需要通过@EnableConfigurationProperties与@ConfigurationProperties配合使用
 */

@Data
@Component
@ConfigurationProperties("mycar")
public class Car {
    private String brand;
    private Integer price;
}
