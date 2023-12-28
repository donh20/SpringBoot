package com.ncamc.admin.mapper;

import com.ncamc.admin.bean.Accounts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    public Accounts getAcc(Long id);
}
