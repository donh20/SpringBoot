package com.ncamc.admin.service.impl;

import com.ncamc.admin.bean.Accounts;
import com.ncamc.admin.mapper.AccountMapper;
import com.ncamc.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    public Accounts getAcctById(Long id){
        return accountMapper.getAcc(id);
    }
}
