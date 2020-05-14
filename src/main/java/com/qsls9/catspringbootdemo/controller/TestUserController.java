package com.qsls9.catspringbootdemo.controller;


import com.qsls9.catspringbootdemo.model.User;
import com.qsls9.catspringbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("mysql/test")
public class TestUserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/select")
    public List<User> select(){
        return userService.select(null);
    }
    @PostMapping(value = "/selectbywxid")
    public Integer selectbywxid(@RequestParam(value = "wxid") String wxid){
        return userService.selectCountbywxid(null);
    }
    @PostMapping(value = "/insert")
    public Integer insert(@RequestParam(value = "wxid") String wxid,
                          @RequestParam(value = "wx_name") String wx_name,
                          @RequestParam(value = "audit_flag") Integer audit_flag,
                          @RequestParam(value = "admin_flag") Integer admin_flag){
        User user = new User();
        user.setAudlt_flag(audit_flag);
        user.setWx_name(wx_name);
        user.setWxid(wxid);
        user.setAdmin_flag(admin_flag);
        return userService.insert(user);
    }
    @PostMapping(value = "/selectAudlt")
    public User selectAudlt(@RequestParam(value = "wxid") String wxid){
        return userService.selectByWxid(null);
    }

    @PostMapping(value = "/selectAdmin")
    public User selectAdmin(){
        return userService.selectAdmin(null);
    }
}
