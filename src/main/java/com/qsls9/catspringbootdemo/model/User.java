package com.qsls9.catspringbootdemo.model;

public class User {
    private Integer id;
    private String wxid;
    private String wx_name;
    private Integer audlt_flag;
    private Integer admin_flag;
    private String robot_id;
    private Integer state;
    private Integer chat_flag;
    private Integer pay_flag;

    public Integer getPay_flag() {
        return pay_flag;
    }

    public void setPay_flag(Integer pay_flag) {
        this.pay_flag = pay_flag;
    }

    public Integer getChat_flag() {
        return chat_flag;
    }

    public void setChat_flag(Integer chat_flag) {
        this.chat_flag = chat_flag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRobot_id() {
        return robot_id;
    }

    public void setRobot_id(String robot_id) {
        this.robot_id = robot_id;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }

    public Integer getAudlt_flag() {
        return audlt_flag;
    }

    public void setAudlt_flag(Integer audlt_flag) {
        this.audlt_flag = audlt_flag;
    }
}
