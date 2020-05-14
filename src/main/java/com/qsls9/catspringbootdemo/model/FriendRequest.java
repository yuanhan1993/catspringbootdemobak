package com.qsls9.catspringbootdemo.model;

public class FriendRequest {
    private Integer id ;
    private String from_wxid;
    private String from_name;
    private String msg;
    private String robot_wxid;
    private Integer deal_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom_wxid() {
        return from_wxid;
    }

    public void setFrom_wxid(String from_wxid) {
        this.from_wxid = from_wxid;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRobot_wxid() {
        return robot_wxid;
    }

    public void setRobot_wxid(String robot_wxid) {
        this.robot_wxid = robot_wxid;
    }

    public Integer getDeal_flag() {
        return deal_flag;
    }

    public void setDeal_flag(Integer deal_flag) {
        this.deal_flag = deal_flag;
    }
}
