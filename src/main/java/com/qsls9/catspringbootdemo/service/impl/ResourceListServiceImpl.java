package com.qsls9.catspringbootdemo.service.impl;

import com.qsls9.catspringbootdemo.dao.ResourceListMapper;
import com.qsls9.catspringbootdemo.model.ResourceList;
import com.qsls9.catspringbootdemo.service.ResourceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceListServiceImpl implements ResourceListService {

    @Autowired
    private ResourceListMapper resourceListMapper;


    @Override
    public List<ResourceList> selectByType(String restype) {
        return resourceListMapper.selectByType(restype);
    }

    @Override
    public ResourceList selectById(Integer id) {
        return resourceListMapper.selectById(id);
    }

    @Override
    public Integer updatePassword(ResourceList resourceList) {
        return resourceListMapper.updatePassword(resourceList);
    }

    @Override
    public Integer selectCountById(Integer id) {
        return resourceListMapper.selectCountById(id);
    }

}
