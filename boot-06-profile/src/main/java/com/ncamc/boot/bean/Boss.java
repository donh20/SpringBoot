package com.ncamc.boot.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile(value = {"prod","default"})
@Component
@ConfigurationProperties("person")
@Data
public class Boss extends Person {
    private String name;
    private Integer age;
}
