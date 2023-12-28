package com.ncamc.admin.service;

import com.ncamc.admin.bean.Accounts;
import com.ncamc.admin.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountMapper accountMapper;

    public Accounts getAcctById(Long id){
        return accountMapper.getAcc(id);
    }
}
