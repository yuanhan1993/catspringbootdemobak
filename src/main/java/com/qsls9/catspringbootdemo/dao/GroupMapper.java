package com.qsls9.catspringbootdemo.dao;

import com.qsls9.catspringbootdemo.model.Group;

public interface GroupMapper {

    Integer insert(Group group);

    Group selectByGroup(Group group);

    Integer updateByGroup(Group group);
}
