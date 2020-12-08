package com.fanlm.service;

import com.fanlm.dao.UserMapper;
import com.fanlm.entity.Action;
import com.fanlm.entity.Role;
import com.fanlm.entity.User;
import com.fanlm.entity.UserExample;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User queryUserByName(String name){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }
    public List<Role> getUserRoles(String name){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        return new ArrayList<>();
    }
    public List<Action> getActions(String name){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);
        return new ArrayList<>();
    }
}
