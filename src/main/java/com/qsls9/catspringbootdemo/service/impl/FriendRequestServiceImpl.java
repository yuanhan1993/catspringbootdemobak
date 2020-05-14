package com.qsls9.catspringbootdemo.service.impl;

import com.qsls9.catspringbootdemo.dao.FriendRequestMapper;
import com.qsls9.catspringbootdemo.model.FriendRequest;
import com.qsls9.catspringbootdemo.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Override
    public FriendRequest select() {
        return friendRequestMapper.select();
    }

    @Override
    public Integer insert(FriendRequest friendRequest) {
        return friendRequestMapper.insert(friendRequest);
    }

    @Override
    public FriendRequest selectById(Integer id) {
        return friendRequestMapper.selectById(id);
    }

    @Override
    public Integer updateById(Integer id) {
        return friendRequestMapper.updateById(id);
    }

    @Override
    public FriendRequest selectByWxid(String wxid) {
        return friendRequestMapper.selectByWxid(wxid);
    }
}
