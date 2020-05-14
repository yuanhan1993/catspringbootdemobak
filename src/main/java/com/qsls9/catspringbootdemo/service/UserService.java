package com.qsls9.catspringbootdemo.service;

import com.qsls9.catspringbootdemo.model.User;

import java.util.List;

public interface UserService {
        List<User> select(String robot_wxid);

        Integer selectCountbywxid(User user);

        Integer insert(User user);

        User selectByWxid(User user);

        Integer updateByWxid(User user);

        User selectAdmin(String robot_wxid);

        User selectById(Integer id);



}
