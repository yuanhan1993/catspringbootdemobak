package com.qsls9.catspringbootdemo.service.impl;

import com.qsls9.catspringbootdemo.dao.GroupMapper;
import com.qsls9.catspringbootdemo.model.Group;
import com.qsls9.catspringbootdemo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Integer insert(Group group) {
       return groupMapper.insert(group);
    }

    @Override
    public Group selectByGroup(Group group) {
        return groupMapper.selectByGroup(group);
    }

    @Override
    public Integer updateByGroup(Group group) {
        return groupMapper.updateByGroup(group);
    }
}
