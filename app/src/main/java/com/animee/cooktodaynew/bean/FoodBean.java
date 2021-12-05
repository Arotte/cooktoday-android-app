package com.animee.cooktodaynew.bean;

public class FoodBean {
    private String id;
    private String name;
    private String short_description;
    private String average_cooking_time;
    private String portions;
    private String ingredients;
    private String cooking_instructions;
    private String long_description;
    private String pub_date;
    private int picId; // we need picture

    public FoodBean(String id, String name, String short_description, String average_cooking_time, String portions,
                    String ingredients, String cooking_instructions, String long_description, String pub_date, int picId) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.average_cooking_time = average_cooking_time;
        this.portions = portions;
        this.ingredients = ingredients;
        this.cooking_instructions = cooking_instructions;
        this.long_description = long_description;
        this.pub_date = pub_date;
        this.picId = picId;
    }

    public FoodBean(String title, String ingredients, String desc, int picId) {

    }

    public FoodBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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
