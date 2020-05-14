package com.qsls9.catspringbootdemo.model;

public class Group {
    private Integer id;
    private String group_id;
    private String group_name;
    private String admin_wxid;
    private String audlt_flag;
    private String robot_id;

    public String getRobot_id() {
        return robot_id;
    }

    public void setRobot_id(String robot_id) {
        this.robot_id = robot_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAdmin_wxid() {
        return admin_wxid;
    }

    public void setAdmin_wxid(String admin_wxid) {
        this.admin_wxid = admin_wxid;
    }

    public String getAudlt_flag() {
        return audlt_flag;
    }

    public void setAudlt_flag(String audlt_flag) {
        this.audlt_flag = audlt_flag;
    }
}
