package com.qsls9.catspringbootdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.qsls9.catspringbootdemo.message.M;
import com.qsls9.catspringbootdemo.model.User;
import com.qsls9.catspringbootdemo.service.UserService;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

public class wxUtils {

    //更新好友列表，调用可爱猫好友列表接口，入库
    public static Integer updateUser(String robot_wxid, UserService userService)  {
        Integer count = 0;
        try {
            JSONArray ja = JSONArray.parseArray(URLDecoder.decode(getImgPost.dealJson(M.get_friend_list(robot_wxid,"1"),"data")));
            for (Object jb : ja){
                User user = new User();
                user.setRobot_id(robot_wxid);
                user.setAudlt_flag(0);
                user.setWx_name(getImgPost.dealJson(jb.toString(),"nickname"));
                user.setWxid(getImgPost.dealJson(jb.toString(),"wxid"));
                user.setAdmin_flag(0);
                if(userService.selectCountbywxid(user)==0){
                    userService.insert(user);
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

}
