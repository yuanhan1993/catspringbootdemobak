package com.qsls9.catspringbootdemo.dao;

import com.qsls9.catspringbootdemo.model.ResourceList;

import java.util.List;

public interface ResourceListMapper {
    List<ResourceList> selectByType(String restype);

    ResourceList selectById(Integer id);

    Integer updatePassword(ResourceList resourceList);

    Integer selectCountById(Integer id );

    List<ResourceList> selectall();
}
