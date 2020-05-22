package com.qsls9.catspringbootdemo.service;

import com.qsls9.catspringbootdemo.model.ResourceList;

import java.util.List;

public interface ResourceListService {
    List<ResourceList> selectByType(String restype);

    ResourceList selectById(Integer id);

    Integer updatePassword(ResourceList resourceList);

    Integer selectCountById(Integer id );


}
