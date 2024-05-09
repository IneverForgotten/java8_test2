package com.fanlm.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanlm.mapper.UserMapper;
import com.fanlm.entity.Action;
import com.fanlm.entity.Role;
import com.fanlm.entity.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User queryUserByName(String name){
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getName, name));
        return users.get(0);
    }
    public List<Role> getUserRoles(String name){
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getName, name));
        return new ArrayList<>();
    }
    public List<Action> getActions(String name){
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getName, name));
        return new ArrayList<>();
    }
}
