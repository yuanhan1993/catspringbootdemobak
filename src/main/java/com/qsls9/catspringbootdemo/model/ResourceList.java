package com.qsls9.catspringbootdemo.model;

public class ResourceList {
    private Integer id ;
    private String title;
    private String link;
    private String password;
    private String imgurl;
    private String restype;
    private String createdate;
    private Integer encryptflag;
    private String extractedcode;

    public String getExtractedcode() {
        return extractedcode;
    }

    public void setExtractedcode(String extractedcode) {
        this.extractedcode = extractedcode;
    }

    public Integer getEncryptflag() {
        return encryptflag;
    }

    public void setEncryptflag(Integer encryptflag) {
        this.encryptflag = encryptflag;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getRestype() {
        return restype;
    }

    public void setRestype(String restype) {
        this.restype = restype;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
