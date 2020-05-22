package com.qsls9.catspringbootdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.qsls9.catspringbootdemo.message.M;
import com.qsls9.catspringbootdemo.model.ResourceList;
import com.qsls9.catspringbootdemo.model.User;
import com.qsls9.catspringbootdemo.service.ResourceListService;
import com.qsls9.catspringbootdemo.service.UserService;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

public class wxUtils {

    //更新好友列表，调用可爱猫好友列表接口，入库
    public static Integer updateUser(String robot_wxid, UserService userService)  {
        Integer count = 0;
        try {
            userService.updateStateByRobotid(robot_wxid);
            JSONArray ja = JSONArray.parseArray(URLDecoder.decode(getImgPost.dealJson(M.get_friend_list(robot_wxid,"1"),"data")));
            for (Object jb : ja){
                User user = new User();
                user.setRobot_id(robot_wxid);
                user.setAudlt_flag(0);
                user.setWx_name(getImgPost.dealJson(jb.toString(),"nickname"));
                user.setWxid(getImgPost.dealJson(jb.toString(),"wxid"));
                user.setAdmin_flag(0);
                user.setState(1);
                user.setChat_flag(0);
                Integer i = userService.selectCountbywxid(user);
                if(i==0){
                    userService.insert(user);
                    count++;
                }else if (i>=1){
                User existsUser = userService.selectByWxid(user);
                existsUser.setState(1);
                userService.updateByWxid(existsUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

//加密存量资源表中的解压密码
    public static Integer encryptPassword(ResourceListService resourceListService){
        Integer count = 0;
        List<ResourceList> resourceLists =  resourceListService.selectByType("0");
        for (ResourceList resourceList : resourceLists){
            try {
                if (resourceList.getEncryptflag()==0){
                    resourceList.setPassword(AesCode.encrypt(resourceList.getPassword()));
                    resourceListService.updatePassword(resourceList);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

}
