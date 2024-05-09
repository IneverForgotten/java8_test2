package com.fanlm.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanlm.entity.User;

@TableName("user")
public interface UserMapper extends BaseMapper<User> {

}