/*
 *  Recipe.java
 *
 *  A simple representation of the Recipe
 *  entity. Mainly functions as a simple
 *  data model with getters & setters.
 *
 */

package com.aron.cooktoday.models;

import com.aron.cooktoday.R;
import com.aron.cooktoday.util.SampleRecipeImgURLs;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

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

    public enum TimeType {
        PREPARATION_TIME,  // time to prepare dish
        COOKING_TIME,      // time to cook dish
        FULL_COOKING_TIME  // sum of the previous two
    }

    // =====================================================
    // fields

    private String name;
    private String shortDescription;
    private String longDescription;
    private String imgUrl;
    private int servings; // unit: piece
    private int calories; // unit: kcal
    private int fullCookTime; // unit: minutes
    private int prepTime;
    private int cookTime;

    // steps:
    private int nSteps;
    private List<String> stepDescriptions;

    // =====================================================
    // constructors

    public Recipe() {
        this.name = "Sample Recipe";
        this.shortDescription = "Short recipe description.";
        this.longDescription = "Long recipe description. Long recipe description. Long recipe description. Long recipe description. Long recipe description.";
        this.imgUrl = SampleRecipeImgURLs.i().get(0);
        this.servings = 3;
        this.calories = 476;
        this.fullCookTime = 91;
        this.prepTime = 22;
        this.cookTime = 51;

        this.nSteps = 0;
    }

    public Recipe(
            String name,
            String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;

        this.shortDescription = "Short recipe description.";
        this.longDescription = "Long recipe description. Long recipe description. Long recipe description. Long recipe description. Long recipe description.";
        this.servings = 2;
        this.calories = 312;
        this.fullCookTime = 91;
        this.prepTime = 22;
        this.cookTime = 51;

        this.nSteps = 0;
    }

    public Recipe(
            String name,
            String imgUrl,
            int servings,
            int calories,
            int fullPrepTime) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.servings = servings;
        this.calories = calories;
        this.fullCookTime = fullPrepTime;

        this.prepTime = 22;
        this.cookTime = 51;
        this.shortDescription = "Short recipe description.";
        this.longDescription = "Long recipe description. Long recipe description. Long recipe description. Long recipe description. Long recipe description.";

        this.nSteps = 0;
    }

    public Recipe(
            String name,
            String imgUrl,
            int servings,
            int calories,
            int fullPrepTime,
            int prepTime,
            int cookTime,
            String shortDescription,
            String longDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imgUrl = imgUrl;
        this.servings = servings;
        this.calories = calories;
        this.fullCookTime = fullPrepTime;
        this.prepTime = prepTime;
        this.cookTime = cookTime;

        this.nSteps = 0;
    }

    // =====================================================
    // getters & setters

    // custom
    public String getTimePretty(TimeType timeType) {
        int time = prepTime;
        switch (timeType){
            case PREPARATION_TIME: {
                time = this.prepTime;
                break;
            } case COOKING_TIME: {
                time = this.cookTime;
                break;
            } case FULL_COOKING_TIME: {
                time = this.fullCookTime;
                break;
            } default: {
                time = prepTime;
                break;
            }
        }
        if (time < 60)
            return time + "m";
        else if (time == 60)
            return "1 hour";
        else {
            int whole = time / 60;
            int mins = time % 60;
            return whole + "h " + mins + "m";
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

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<String> getStepDescriptions() {
        return stepDescriptions;
    }

    public void setStepDescriptions(List<String> stepDescriptions) {
        this.stepDescriptions = stepDescriptions;
        this.nSteps = stepDescriptions.size();
    }

    public void addStep(String stepText) {
        this.stepDescriptions.add(stepText);
        this.nSteps++;
    }

    public void removeStep(int position) {
        this.stepDescriptions.remove(position);
        this.nSteps--;
    }
}
