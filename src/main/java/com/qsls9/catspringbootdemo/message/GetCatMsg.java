package com.qsls9.catspringbootdemo.message;

import com.alibaba.fastjson.JSON;
import com.qsls9.catspringbootdemo.model.FriendRequest;
import com.qsls9.catspringbootdemo.model.ResourceList;
import com.qsls9.catspringbootdemo.service.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class GetCatMsg {
    @Autowired
    private UserService userService;
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private ResourceListService resourceListService;

    @RequestMapping("/sendMsg")
    @CrossOrigin(origins = "*")
    @ResponseBody
    public String getMsg(HttpServletRequest request) throws IOException, JSONException {
        return M.send_demo(request,request.getParameter("type"),userService,friendRequestService,groupService,groupUserService,resourceListService);
    }
}
