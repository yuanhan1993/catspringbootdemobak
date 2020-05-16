package com.qsls9.catspringbootdemo.service;

import com.qsls9.catspringbootdemo.model.GroupUser;

public interface GroupUserService {
    Integer insert(GroupUser groupUser);

    GroupUser selectByGroupUser(GroupUser groupUser);

}
