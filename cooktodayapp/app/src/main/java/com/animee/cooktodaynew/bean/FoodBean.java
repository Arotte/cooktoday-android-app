package com.animee.cooktodaynew.bean;

public class FoodBean {
    private String title;
    private String ingredients;
    private String desc;
    private int picId;

    public FoodBean(String title, String ingredients, String desc, int picId) {
        this.title = title;
        this.ingredients = ingredients;
        this.desc = desc;
        this.picId = picId;
    }
}
