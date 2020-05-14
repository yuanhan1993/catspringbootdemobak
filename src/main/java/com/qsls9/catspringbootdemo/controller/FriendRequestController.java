package com.qsls9.catspringbootdemo.controller;

import com.qsls9.catspringbootdemo.model.FriendRequest;
import com.qsls9.catspringbootdemo.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("friend")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping(value = "/select")
    public FriendRequest select(){
        return friendRequestService.select();
    }
}
