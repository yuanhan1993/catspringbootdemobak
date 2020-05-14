package com.qsls9.catspringbootdemo.dao;

import com.qsls9.catspringbootdemo.model.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

public interface UserMapper {

    List<User> select(String robot_wxid);

    Integer selectCountbywxid(User user);

    Integer insert(User user);

    User selectByWxid(User user);

    Integer updateByWxid(User user);

    User selectAdmin(String robot_wxid);

    User selectById(Integer id );

}
