package com.qsls9.catspringbootdemo.service.impl;

import com.qsls9.catspringbootdemo.dao.GroupUserMapper;
import com.qsls9.catspringbootdemo.model.GroupUser;
import com.qsls9.catspringbootdemo.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupUerServiceImpl implements GroupUserService {
    @Autowired
    private GroupUserMapper groupUserMapper;


    @Override
    public Integer insert(GroupUser groupUser) {
        return groupUserMapper.insert(groupUser);
    }

    @Override
    public GroupUser selectByGroupUser(GroupUser groupUser) {
        return groupUserMapper.selectByGroupUser(groupUser);
    }
}
