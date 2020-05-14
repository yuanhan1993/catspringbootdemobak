package com.qsls9.catspringbootdemo.service;

import com.qsls9.catspringbootdemo.model.FriendRequest;

public interface FriendRequestService {

    FriendRequest select ();

    Integer insert(FriendRequest friendRequest);

    FriendRequest selectById(Integer id);

    Integer updateById(Integer id);

    FriendRequest selectByWxid(String wxid);

}
