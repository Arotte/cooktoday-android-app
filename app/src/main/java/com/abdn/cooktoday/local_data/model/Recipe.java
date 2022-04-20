/*
 *  Recipe.java
 *
 *  A simple representation of the Recipe
 *  entity. Mainly functions as a simple
 *  data model with getters & setters.
 *
 */

package com.abdn.cooktoday.local_data.model;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON;
import com.abdn.cooktoday.utility.SampleRecipeImgURLs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String serverId;

    private String name;
    private String shortDescription;
    private String longDescription;
    private String imgUrl;
    private int servings; // unit: piece
    private int calories; // unit: kcal
    private int fullCookTime; // unit: minutes
    private int prepTime;
    private int cookTime;

    private boolean cookedByUser;
    private boolean isSaved;

    // steps:
    private int nSteps;
    private List<String> stepDescriptions;

    // ingreds
    private int nIngreds;
    private List<Ingredient> ingredients; // TODO: list of Ingredient class


    // =====================================================
    // constructors

    public Recipe(RecipeJSON recipeJson) {
        String recipeImgUrl = "";
        if (!recipeJson.getMedia().isEmpty())
            recipeImgUrl = recipeJson.getMedia().get(0);

        this.serverId = recipeJson.getId();
        this.name = recipeJson.getName();
        this.shortDescription = recipeJson.getShortDesc();
        this.longDescription = recipeJson.getLongDesc();
        this.imgUrl = recipeImgUrl;
        this.servings = recipeJson.getPortionsNum();
        this.calories = recipeJson.getCalories();
        this.prepTime = recipeJson.getPrepTime();
        this.cookTime = recipeJson.getCookingTime();
        this.fullCookTime = this.prepTime + this.cookTime;
        this.stepDescriptions = recipeJson.getInstructionsStr();
        this.nSteps = stepDescriptions.size();
        this.ingredients = recipeJson.getIngredientsIngred();
        this.nIngreds = ingredients.size();
        this.cookedByUser = false;
        this.isSaved = false;
    }

    public Recipe() {
        this.serverId = "";
        this.name = "Sample Recipe";
        this.shortDescription = "Short recipe description.";
        this.longDescription = "Long recipe description. Long recipe description. Long recipe description. Long recipe description. Long recipe description.";
        this.imgUrl = SampleRecipeImgURLs.i().get(0);
        this.servings = 3;
        this.calories = 476;
        this.fullCookTime = 91;
        this.prepTime = 22;
        this.cookTime = 51;

        this.stepDescriptions = new ArrayList<>(Arrays.asList(
                "In a medium-size mixing bowl or large glass measuring cup, <b>whisk together</b> your dry ingredients.",
                "Heat olive oil in a large, oven-proof non stick pan (or a well-seasoned cast iron skillet) over medium-high heat. Sear chicken thighs for 3 minutes each side, until the skin becomes golden and crisp. Leave 2 tablespoons of chicken juices in the pan for added flavour, and drain any excess.",
                "Fry the garlic in the same pan around the chicken for 1 minute until fragrant. Add the honey, both mustards, and water to the pan, mixing well, and combine all around the chicken.",
                "OPTIONAL: Remove from the oven after 30 minutes; add in the green beans (mixing them through the sauce), and return to the oven to bake for a further 15 minutes, or until the chicken is completely cooked through and no longer pink in the middle, and the potatoes are fork tender."
        ));
        this.nSteps = this.stepDescriptions.size();

        this.ingredients = new ArrayList<>(Arrays.asList(
            new Ingredient("Eggs", "2 pieces"),
            new Ingredient("Bacon", "200g"),
            new Ingredient("Water", "1l"),
            new Ingredient("Weed", "10g"),
            new Ingredient("Sliced bacon", "100g"),
            new Ingredient("Salt", "to taste")
        ));
        this.nIngreds = this.ingredients.size();
        this.cookedByUser = false;
        this.isSaved = false;
    }

    public Recipe(
            String name,
            String imgUrl) {
        this.serverId = "";
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
        this.cookedByUser = false;
        this.isSaved = false;
    }

    public Recipe(
            String name,
            String imgUrl,
            int servings,
            int calories,
            int fullPrepTime) {
        this.serverId = "";
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
        this.cookedByUser = false;
        this.isSaved = false;
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
        this.serverId = "";
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
        this.cookedByUser = false;
        this.isSaved = false;
    }

    public Recipe(String name,
                  String shortDescription,
                  String longDescription,
                  String imgUrl,
                  int servings,
                  int calories,
                  int prepTime,
                  int cookTime,
                  List<String> stepDescriptions,
                  List<Ingredient> ingredients) {
        this.serverId = "";
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imgUrl = imgUrl;
        this.servings = servings;
        this.calories = calories;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.fullCookTime = this.prepTime + this.cookTime;
        this.stepDescriptions = stepDescriptions;
        this.nSteps = stepDescriptions.size();
        this.ingredients = ingredients;
        this.nIngreds = ingredients.size();
        this.cookedByUser = false;
        this.isSaved = false;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fullCookTime=" + fullCookTime +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", nSteps=" + nSteps +
                ", stepDescriptions=" + stepDescriptions +
                ", nIngreds=" + nIngreds +
                ", ingredients=" + ingredients +
                '}';
    }

    public List<String> getIngredientsStr() {
        ArrayList<String> ret = new ArrayList<>();
        for (Ingredient ingred : ingredients)
            ret.add(ingred.getName());
        return ret;
    }

    // default


    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public boolean isCookedByUser() {
        return cookedByUser;
    }

    public void setCookedByUser(boolean cookedByUser) {
        this.cookedByUser = cookedByUser;
    }

    public String getServerId() {
        return serverId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

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
