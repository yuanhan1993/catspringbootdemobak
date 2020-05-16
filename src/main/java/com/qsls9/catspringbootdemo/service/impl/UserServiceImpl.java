package com.qsls9.catspringbootdemo.service.impl;

import com.qsls9.catspringbootdemo.dao.UserMapper;
import com.qsls9.catspringbootdemo.model.User;
import com.qsls9.catspringbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> select(String robot_wxid) {
        return userMapper.select(robot_wxid);
    }

    @Override
    public Integer selectCountbywxid(User user) {
        return userMapper.selectCountbywxid(user);
    }


    @Override
    public Integer insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User selectByWxid(User user) {
        return userMapper.selectByWxid(user);
    }

    @Override
    public Integer updateByWxid(User user) {
        return userMapper.updateByWxid(user);
    }

    @Override
    public User selectAdmin(String robot_wxid) {
        return userMapper.selectAdmin(robot_wxid);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public Integer updateStateByRobotid(String robot_wxid) {
        return userMapper.updateStateByRobotid(robot_wxid);
    }

}
