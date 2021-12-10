package com.animee.cooktodaynew.bean;

public class FoodBean {
    private String title;
    private String notmatch;
    private String desc;
    private int picId;

    public FoodBean(String title, String notmatch, String desc, int picId) {
        this.title = title;
        this.notmatch = notmatch;
        this.desc = desc;
        this.picId = picId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotmatch() {
        return notmatch;
    }

    public void setNotmatch(String notmatch) {
        this.notmatch = notmatch;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }
}
