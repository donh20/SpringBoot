package com.ncamc.admin.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Accounts {
    private Long id;
    private String name;
    private BigDecimal balance;
}
