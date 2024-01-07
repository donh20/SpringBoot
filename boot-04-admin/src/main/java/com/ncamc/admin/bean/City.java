package com.ncamc.admin.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {

    private Integer id;

    private String name;

    private String state;

    private String country;

}
