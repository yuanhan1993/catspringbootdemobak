package com.qsls9.catspringbootdemo.dao;

import com.qsls9.catspringbootdemo.model.FriendRequest;

public interface FriendRequestMapper {
    FriendRequest select ();

    Integer insert(FriendRequest friendRequest);

    FriendRequest selectById(Integer id);

    Integer updateById(Integer id);

    FriendRequest selectByWxid(String wxid);
}
