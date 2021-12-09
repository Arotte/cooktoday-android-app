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

    public FoodBean(String id, String name, String short_description, String average_cooking_time,
                    String portions, String ingredients, String cooking_instructions,
                    String long_description, String pub_date, int picId) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getAverage_cooking_time() {
        return average_cooking_time;
    }

    public void setAverage_cooking_time(String average_cooking_time) {
        this.average_cooking_time = average_cooking_time;
    }

    public String getPortions() {
        return portions;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCooking_instructions() {
        return cooking_instructions;
    }

    public void setCooking_instructions(String cooking_instructions) {
        this.cooking_instructions = cooking_instructions;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }
}
