/*
 *  Recipe.java
 *
 *  A simple representation of the Recipe
 *  entity. Mainly functions as a simple
 *  data model with getters & setters.
 *
 */

package com.aron.cooktoday.models;

import com.aron.cooktoday.util.SampleRecipeImgURLs;

import java.io.Serializable;

public class Recipe implements Serializable {
    /*
    * Note: it implements the Serializable class
    * to able to pass it between activities.
    *
    * An example of passing and retrieving
    * a Recipe object between activities:
    *
    * Activity1:
    * intent.putExtra("MyRecipe", myRecipeObject);
    *
    * Activity2:
    * getIntent().getSerializableExtra("MyRecipe");
    *
    * Source: https://stackoverflow.com/a/2736612/4745591
    * */

    // =====================================================
    // fields

    private String name;
    private String imgUrl;
    private int servings; // unit: piece
    private int calories; // unit: kcal
    private int fullCookTime; // unit: minutes

    // =====================================================
    // constructors

    public Recipe() {
        this.name = "Sample Recipe";
        this.imgUrl = SampleRecipeImgURLs.i().get(0);
        this.servings = 3;
        this.calories = 476;
        this.fullCookTime = 91;
    }

    public Recipe(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;

        this.servings = 2;
        this.calories = 312;
        this.fullCookTime = 91;
    }

    public Recipe(String name, String imgUrl, int servings, int calories, int fullPrepTime) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.servings = servings;
        this.calories = calories;
        this.fullCookTime = fullPrepTime;
    }

    // =====================================================
    // getters & setters

    // custom
    public String getFullPrepTimePretty() {
        if (fullCookTime < 60)
            return fullCookTime + " mins";
        else if (fullCookTime == 60)
            return "1 hour";
        else {
            int whole = fullCookTime / 60;
            int mins = fullCookTime % 60;
            return whole + " h " + mins + " m";
        }
    }

    // default

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFullCookTime() {
        return fullCookTime;
    }

    public void setFullCookTime(int fullPrepTime) {
        this.fullCookTime = fullPrepTime;
    }
}
