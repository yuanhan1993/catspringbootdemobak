package com.qsls9.catspringbootdemo.service;

import com.qsls9.catspringbootdemo.model.Group;

public interface GroupService {
    Integer insert(Group group);

    Group selectByGroup(Group group);

    Integer updateByGroup(Group group);

}
