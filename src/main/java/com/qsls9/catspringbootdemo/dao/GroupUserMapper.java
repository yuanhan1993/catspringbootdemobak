package com.qsls9.catspringbootdemo.dao;


import com.qsls9.catspringbootdemo.model.GroupUser;

public interface GroupUserMapper {

    Integer insert(GroupUser groupUser);

    GroupUser selectByGroupUser(GroupUser groupUser);
}
