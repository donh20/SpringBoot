package com.ncamc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncamc.admin.bean.User;
import com.ncamc.admin.mapper.UserMapper;
import com.ncamc.admin.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}