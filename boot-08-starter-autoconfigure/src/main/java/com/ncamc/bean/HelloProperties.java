package com.ncamc.bean;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.sql.DataSourceDefinition;


@Data
@ConfigurationProperties("ncamc.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;
}
