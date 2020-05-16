package com.qsls9.catspringbootdemo.message;

import com.alibaba.fastjson.JSONObject;
import com.qsls9.catspringbootdemo.model.FriendRequest;
import com.qsls9.catspringbootdemo.model.Group;
import com.qsls9.catspringbootdemo.model.GroupUser;
import com.qsls9.catspringbootdemo.model.User;
import com.qsls9.catspringbootdemo.service.FriendRequestService;
import com.qsls9.catspringbootdemo.service.GroupService;
import com.qsls9.catspringbootdemo.service.GroupUserService;
import com.qsls9.catspringbootdemo.service.UserService;
import com.qsls9.catspringbootdemo.util.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;
import java.util.List;

public class M {
    private static String url = "http://192.168.0.103:8073/send";
    private static String AUDITMODE = "看美女，图片，开车，飙车 ";
    private static String AUDLTFLAG = "  开启飙车模式  ,  开启隐藏模式  ";


    //测试调用方法
    public static  String send_demo(HttpServletRequest request, String type, UserService userService, FriendRequestService friendRequestService, GroupService groupService, GroupUserService groupUserService) throws IOException, JSONException {
        String return_info = null;
        String robot_wxid = request.getParameter("robot_wxid");
        String from_wxid = request.getParameter("final_from_wxid");
        String from_name = request.getParameter("final_from_name");
        String msg = request.getParameter("msg");
        //聊天请求
        if ("100".equals(type)) {
            User user1 = new User();
            user1.setWxid(from_wxid);
            user1.setWx_name(from_name);
            user1.setAudlt_flag(0);
            user1.setAdmin_flag(0);
            user1.setRobot_id(robot_wxid);
            user1.setState(1);
            user1.setChat_flag(0);
            if (userService.selectCountbywxid(user1) == 0) {
                userService.insert(user1);
            }
            if ("我的wxid".equals(msg)) {
                return_info = send_text_msg(robot_wxid, from_wxid, from_wxid);
            } else if (AUDITMODE.contains(msg)) {
                if (userService.selectByWxid(user1).getAudlt_flag() == 0) {
                    return_info = send_text_msg(robot_wxid,from_wxid,"您没有开启飙车模式，请发送 “开启飙车模式 ” 开启飙车功能");  //暂时关闭飙车模式
/*                    return_info = send_text_msg(robot_wxid, from_wxid, "飙车模式暂时关闭，需要开启权限请联系主人");*/
                } else {
                    return_info = send_image_msg(robot_wxid, from_wxid, getImgPost.getimg());
                }
            } else if(msg.contains("更新好友列表")){
                if (userService.selectByWxid(user1).getAdmin_flag()==1){
                   Integer count =  wxUtils.updateUser(robot_wxid,userService);
                    send_text_msg(robot_wxid,from_wxid,"已更新好友列表，更新条数为 ：" +count +" 条") ;
                }else {
                    send_text_msg(robot_wxid,from_wxid,"您不是管理员，无权进行此操作");
                }
            }
            //主人id查询好友列表
            else if(msg.contains("查看好友列表")){
                if (userService.selectByWxid(user1).getAdmin_flag()==1){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("好友列表为：\n");
                   List<User> users = userService.select(robot_wxid);
                   for (User user : users){
                       stringBuilder.append("id为： "+user.getId()).append("\n昵称为：  "+user.getWx_name())
                               .append("\n开车模式开关：  "+(user.getAudlt_flag()==1?"开启":"关闭")).append("\n").append("状态： ")
                       .append(user.getState()==1?"正常":"删除").append("\n");
                   }
                    return_info =send_text_msg(robot_wxid,from_wxid,stringBuilder.toString());
                }else {
                    return_info =send_text_msg(robot_wxid,from_wxid,"您不是管理员，无权进行此操作");
                }
            }
            //给好友发送消息
            else if(msg.contains("发送好友消息")){
                if (userService.selectByWxid(user1).getAdmin_flag()==1){
                    String[] tmp = msg.split("：");
                    if (tmp[1]!=null){
                        User u =userService.selectById(Integer.valueOf(tmp[1]));
                        if (u!=null){
                            if (tmp[2]!=null){
                                if (tmp[2].contains("图片") || tmp[2].contains("美女")){
                                    return_info = send_image_msg(robot_wxid, u.getWxid(), getImgPost.getimg());
                                }else if(tmp[2].contains("轰炸") || tmp[2].contains("十连发")){
                                    for (int i=0;i<30;i++){
                                        return_info = send_image_msg(robot_wxid, u.getWxid(), getImgPost.getimg());
                                    }
                                }
                                    else {
                                    return_info = send_text_msg(robot_wxid,u.getWxid(),tmp[2]);
                                }
                            }else {
                                return_info =send_text_msg(robot_wxid,from_wxid,"发送消息不能为空");
                            }
                        }else {
                            return_info =send_text_msg(robot_wxid,from_wxid,"好友id不存在");
                        }
                    }else {
                        return_info =send_text_msg(robot_wxid,from_wxid,"好友id不能为空");
                    }
                }else{
                    return_info =send_text_msg(robot_wxid,from_wxid,"您不是管理员，无权进行此操作");

                }
            }

            //通过主人id发消息同意好友请求
            else if (msg.contains("同意好友")) {
                String[] tmp = msg.split("：");
                if (userService.selectByWxid(user1).getAdmin_flag() == 1) {
                    FriendRequest friendRequest = friendRequestService.selectById(Integer.valueOf(tmp[1]));
                    if (friendRequest != null){
                        if (friendRequest.getDeal_flag() == 0) {
                            agree_friend_verify(robot_wxid, URLEncoder.encode(friendRequest.getMsg(),"UTF-8"));
                            friendRequestService.updateById(Integer.valueOf(tmp[1]));
                            return_info = send_text_msg(robot_wxid, from_wxid, "已同意该好友请求");
                        } else {
                            return_info = send_text_msg(robot_wxid, from_wxid, "已同意过改好友请求，无需再次同意");
                        }
                    }else {
                        return_info = send_text_msg(robot_wxid, from_wxid, "没有该好友申请，请检查id是否正确");
                    }
                } else {
                    return_info = send_text_msg(robot_wxid, from_wxid, "您没有权限进行同意好友操作");

                }

            }
            //暂时关闭飙车模式
            else if(AUDLTFLAG.contains(msg)){
                if (userService.selectByWxid(user1).getAudlt_flag()==1){
                    return_info = send_text_msg(robot_wxid,from_wxid,"您已开启飙车模式，无需重复开启");
                }else{
                    User user = new User();
                    user.setWxid(from_wxid);
                    user.setWx_name(from_name);
                    user.setAudlt_flag(1);
                    if(userService.updateByWxid(user)==1){
                        return_info = send_text_msg(robot_wxid,from_wxid,"恭喜您！！！  开启成功   尽情飙车吧！！！！！！");
                    }else {
                        return_info = send_text_msg(robot_wxid,from_wxid,"开启失败，请联系系统管理员开启权限");
                    }
                }
            }
            else {
                if(userService.selectByWxid(user1).getChat_flag()==0){
                    return_info = send_text_msg(robot_wxid, from_wxid, "您未开启自由聊天模式，请发送功能关键字");
                } else {
                    //调用图灵机器人接口
                    msg = postUtils.TLRobot(msg);
                    if ("请求次数超限制!".equals(msg)) {
                        //调用青客云接口
                        msg = newHttpUtils.getMessage(msg);
                    }
                    return_info = send_text_msg(robot_wxid, from_wxid, msg);
                }
                }
        }

        //加好友请求
        if("500".equals(type)){
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setFrom_wxid(from_wxid);
            friendRequest.setFrom_name(from_name);
            friendRequest.setDeal_flag(0);
            friendRequest.setMsg(msg);
            friendRequest.setRobot_wxid(robot_wxid);
            String valid_msg = getImgPost.dealJson(msg,"from_content") ;
            if (friendRequestService.insert(friendRequest)==1){
                return_info = send_text_msg(robot_wxid,userService.selectAdmin(robot_wxid).getWxid(),"收到好友请求，微信名为:"+from_name+"\n微信号为:"+from_wxid+"\n验证消息为："+valid_msg+"\nid为："+friendRequestService.selectByWxid(from_wxid).getId());
            }
            else{
                return_info = send_text_msg(robot_wxid,userService.selectAdmin(robot_wxid).getWxid(),"插入好友申请表失败，微信名为:"+from_name+"\n微信号为:"+from_wxid+"\n验证消息为："+valid_msg+"\nid为："+friendRequestService.selectByWxid(from_wxid).getId());
            }
        }
        if("200".equals(type)){
            String group_id = request.getParameter("from_wxid");
            String group_name = request.getParameter("from_name");
            Group group = new Group();
            group.setGroup_id(group_id);
            group.setGroup_name(group_name);
            group.setAudlt_flag(0);
            group.setRobot_id(robot_wxid);
            if (groupService.selectByGroup(group)==null){
                if (groupService.insert(group)==1){
                    GroupUser groupUser = new GroupUser();
                    groupUser.setGroupId(groupService.selectByGroup(group).getId());
                    groupUser.setRobot_id(robot_wxid);
                    groupUser.setWxid(from_wxid);
                    groupUser.setWx_name(from_name);
                    if (groupUserService.selectByGroupUser(groupUser)==null){
                        groupUserService.insert(groupUser);
                    }
                }

            }else {
                if (groupService.selectByGroup(group).getChat_flag()==1){
                    msg = postUtils.TLRobot(msg);
                    if ("请求次数超限制!".equals(msg)) {
                        //调用青客云接口
                        msg = newHttpUtils.getMessage(msg);
                    }
                    return_info=send_group_at_msg(robot_wxid,group_id,from_wxid,from_name,msg);
                }
            }
        }

            return  return_info;
    }

    /**
     * 发送文字消息(好友或者群)
     *
     * @param robot_wxid String robot_wxid 登录账号id，用哪个账号去发送这条消息
     * @param to_wxid    String to_wxid 对方的id，可以是群或者好友id
     * @param msg        String msg     消息内容
     * @return string json_string
     */
    public static String send_text_msg(String robot_wxid, String to_wxid, String msg) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "100");// Api数值（可以参考 - api列表demo）
        map.put("msg", URLEncoder.encode(msg, "UTF-8"));// 发送内容
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送群消息并艾特某人
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id，用哪个账号去发送这条消息
     * String to_wxid 群id
     * String at_wxid 艾特的id，群成员的id
     * String at_name 艾特的昵称，群成员的昵称
     * String msg     消息内容
     *
     * @return string json_string
     */
    public static String send_group_at_msg(String robot_wxid, String to_wxid, String at_wxid, String at_name, String msg) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "102");// Api数值（可以参考 - api列表demo）
        map.put("msg", URLEncoder.encode(msg, "UTF-8"));// 发送内容
        map.put("to_wxid", to_wxid);// 对方id
        map.put("at_wxid", at_wxid);// 艾特的id，群成员的id
        map.put("at_name", at_name);// 艾特的昵称，群成员的昵称
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送图片消息
     * @about qsls9
     * @about qsls9
     * String robot_wxid 登录账号id，用哪个账号去发送这条消息
     * String to_wxid 对方的id，可以是群或者好友id
     * String path    图片的绝对路径
     *
     * @return string json_string
     */
    public static String send_image_msg(String robot_wxid, String to_wxid, String path) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "103");// Api数值（可以参考 - api列表demo）
        map.put("msg", path);// 发送的图片的绝对路径
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送视频消息
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id，用哪个账号去发送这条消息
     * String to_wxid 对方的id，可以是群或者好友id
     * String path    视频的绝对路径
     *
     * @return string json_string
     */
    public String send_video_msg(String robot_wxid, String to_wxid, String path) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "104");// Api数值（可以参考 - api列表demo）
        map.put("msg", path);// 发送的视频的绝对路径
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送文件消息
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id，用哪个账号去发送这条消息
     * String to_wxid 对方的id，可以是群或者好友id
     * String path    文件的绝对路径
     *
     * @return string json_string
     */
    public String send_file_msg(String robot_wxid, String to_wxid, String path) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "105");// Api数值（可以参考 - api列表demo）
        map.put("msg", path);// 发送的文件的绝对路径
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送动态表情
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id，用哪个账号去发送这条消息
     * String to_wxid 对方的id，可以是群或者好友id
     * String path    动态表情文件（通常是gif）的绝对路径
     *
     * @return string json_string
     */
    public String send_emoji_msg(String robot_wxid, String to_wxid, String path) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "106");// Api数值（可以参考 - api列表demo）
        map.put("msg", path);// 发送的文件的绝对路径
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送分享链接
     * @about qsls9
     * @about qsls9
     * String robot_wxid    账户id，用哪个账号去发送这条消息
     * String to_wxid    对方的id，可以是群或者好友id
     * $title      链接标题
     * $text       链接内容
     * $target_url 跳转链接
     * $pic_url    图片链接
     *
     * @return string json_string
     */
    public String send_link_msg(String robot_wxid, String to_wxid, String title, String text, String target_url, String pic_url) throws IOException {

        // 封装链接结构体
        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> link = new HashMap<String, String>();
        link.put("title", title);
        link.put("text", text);
        link.put("url", target_url);
        link.put("pic", pic_url);

        // 封装返回数据结构
        map.put("type", "107");// Api数值（可以参考 - api列表demo）
        map.put("msg", link.toString());// 发送的分享链接结构体
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 发送音乐分享
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id，用哪个账号去发送这条消息
     * String to_wxid 对方的id，可以是群或者好友id
     * $name    歌曲名字
     *
     * @return string json_string
     */
    public String send_music_msg(String robot_wxid, String to_wxid, String name) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "108");// Api数值（可以参考 - api列表demo）
        map.put("msg", URLEncoder.encode(name, "UTF-8"));// 歌曲名字
        map.put("to_wxid", to_wxid);// 对方id
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取指定登录账号的昵称
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id
     *
     * @return string 账号昵称
     */
    public String get_robot_name(String robot_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "201");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取指定登录账号的头像
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id
     *
     * @return string 头像http地址
     */
    public String get_robot_headimgurl(String robot_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "202");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取登录账号列表
     * @about qsls9
     * @about qsls9
     * String robot_wxid 账户id
     *
     * @return string 当前框架已登录的账号信息列表
     */
    public String get_logged_account_list() throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "203");// Api数值（可以参考 - api列表demo）
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取好友列表
     * @about qsls9
     * @about qsls9
     * String robot_wxid    账户id
     * $is_refresh 是否刷新
     *
     * @return string 当前框架已登录的账号信息列表
     */
    public static String get_friend_list(String robot_wxid, String is_refresh) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "204");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid); // 账户id（可选，如果填空字符串，即取所有登录账号的好友列表，反正取指定账号的列表）
        map.put("is_refresh", is_refresh);// 是否刷新列表，0 从缓存获取 / 1 刷新并获取
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取群聊列表
     * @about qsls9
     * @about qsls9
     * String robot_wxid    账户id
     * $is_refresh 是否刷新
     *
     * @return string 当前框架已登录的账号信息列表
     */
    public String get_group_list(String robot_wxid, String is_refresh) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "205");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("is_refresh", is_refresh);// 是否刷新
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 取群成员列表
     * @about qsls9
     * @about qsls9
     * String robot_wxid    账户id
     * String group_wxid 群id
     * $is_refresh 是否刷新，0 从缓存获取 / 1 刷新并获取
     *
     * @return string 当前框架已登录的账号信息列表
     */
    public String get_group_member_list(String robot_wxid, String group_wxid, String is_refresh) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "206");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("is_refresh", is_refresh);// 是否刷新列表，0 从缓存获取 / 1 刷新并获取
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));

    }


    /**
     * 取群成员资料
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String group_wxid  群id
     * $member_wxid 群成员id
     *
     * @return string json_string
     */
    public String get_group_member(String robot_wxid, String group_wxid, String member_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "207");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("member_wxid", member_wxid);// 群成员id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 接收好友转账
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String friend_wxid 朋友id
     * $json_string 转账事件原消息
     *
     * @return string json_string
     */
    public String accept_transfer(String robot_wxid, String friend_wxid, String json_string) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "301");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("friend_wxid", friend_wxid);// 朋友id
        map.put("msg", json_string);// 群成员id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 同意群聊邀请
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * $json_string 同步消息事件中群聊邀请原消息
     *
     * @return string json_string
     */
    public String agree_group_invite(String robot_wxid, String json_string) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "302");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("msg", json_string);// 同步消息事件中群聊邀请原消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 同意好友请求
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * $json_string 好友请求事件中原消息
     *
     * @return string json_string
     */
    public static String agree_friend_verify(String robot_wxid, String json_string) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "303");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("msg", json_string);// 同步消息事件中群聊邀请原消息
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }

    /**
     * 修改好友备注
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String friend_wxid 好友id
     * $note 新备注（空字符串则是删除备注）
     *
     * @return string json_string
     */
    public String modify_friend_note(String robot_wxid, String friend_wxid, String note) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "304");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("friend_wxid", friend_wxid);// 朋友id
        map.put("note", URLEncoder.encode(note, "UTF-8"));// 新备注（空字符串则是删除备注）
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 删除好友
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String friend_wxid 好友id
     *
     * @return string json_string
     */
    public String delete_friend(String robot_wxid, String friend_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "305");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("friend_wxid", friend_wxid);// 朋友id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 踢出群成员
     * String robot_wxid     账户id
     * String group_wxid  群id
     * $member_wxid 群成员id
     *
     * @return string json_string
     */
    public String remove_group_member(String robot_wxid, String group_wxid, String member_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "306");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("member_wxid", member_wxid);// 群成员id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 修改群名称
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String group_wxid  群id
     * String group_name  新群名
     *
     * @return string json_string
     */
    public String modify_group_name(String robot_wxid, String group_wxid, String group_name) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "307");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("group_name", group_name);// 新群名
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }

    /**
     * 修改群公告
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String group_wxid  群id
     * $notice      新公告
     *
     * @return string json_string
     */
    public String modify_group_notice(String robot_wxid, String group_wxid, String notice) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "308");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("notice", notice);// 新公告
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 建立新群
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     *
     * @param friends<String>     三个人及以上的好友id数组，['wxid_1xxx', 'wxid_2xxx', 'wxid_3xxx', 'wxid_4xxx']
     * @return string json_string
     */
    public String building_group(String robot_wxid, ArrayList<String> friends) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "309");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("friends", friends.toString());// 三个人及以上的好友id数组
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }


    /**
     * 退出群聊
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String group_wxid  群id
     *
     * @return string json_string
     */
    public String quit_group(String robot_wxid, String group_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "310");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }

    /**
     * 邀请加入群聊
     * @about qsls9
     * @about qsls9
     * String robot_wxid     账户id
     * String group_wxid  群id
     * String friend_wxid 好友id
     *
     * @return string json_string
     */
    public String invite_in_group(String robot_wxid, String group_wxid, String friend_wxid) throws IOException {
        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "311");// Api数值（可以参考 - api列表demo）
        map.put("robot_wxid", robot_wxid);// 账户id，用哪个账号去发送这条消息
        map.put("group_wxid", group_wxid);// 群id
        map.put("friend_wxid", friend_wxid);// 好友id
        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }
}
