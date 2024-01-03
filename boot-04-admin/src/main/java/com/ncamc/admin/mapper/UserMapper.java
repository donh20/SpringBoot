package com.ncamc.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncamc.admin.bean.User;

/**
 * 有了MyBatis plus以后,框架提供了一个基类Mapper,叫BaseMapper
 * 想要操作哪个类型的数据就传入表名实体类,这样就有了CRUD能力
 */
public interface UserMapper extends BaseMapper<User> {
}
