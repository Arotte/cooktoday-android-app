/*
 *  Recipe.java
 *
 *  A simple representation of the Recipe
 *  entity. Mainly functions as a simple
 *  data model with getters & setters.
 *
 */

package com.abdn.cooktoday.local_data.model;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.RecipeIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJson;

import java.io.Serializable;
import java.util.ArrayList;
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

    // default fields
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
    private String cuisine;

    // internal fields
    private boolean cookedByUser;
    private boolean isSaved;
    private boolean createdByUser;

    // steps:
    private int nSteps;
    private List<String> stepDescriptions;

    // ingredients:
    private int nIngreds;
    private List<Ingredient> ingredients; // TODO: list of Ingredient class
    private List<Boolean> isIngredChecked;


    // =====================================================
    // constructors

    public Recipe() {
    }

    /**
     * Constructs a Recipe object from a RecipeJson object
     * @param recipeJson the RecipeJson object
     */
    public Recipe(RecipeJson recipeJson) {
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
        this.cookedByUser = false;
        this.isSaved = false;
        this.cuisine = recipeJson.getCuisine();

        this.ingredients = new ArrayList<>();
        this.isIngredChecked = new ArrayList<>();
        for (RecipeIngredientJson recipeIngredientJson : recipeJson.getIngredients()) {
            this.ingredients.add(new Ingredient(recipeIngredientJson));
            this.isIngredChecked.add(false);
        }
        this.nIngreds = ingredients.size();

        this.createdByUser = false;
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
                  List<Ingredient> ingredients,
                  String cuisine) {
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
        this.cuisine = cuisine;

        this.createdByUser = false;
        this.isIngredChecked = new ArrayList<>();
        for (int i = 0; i < nIngreds; i++)
            this.isIngredChecked.add(false);
    }

    // =====================================================
    // getters & setters


    /**
     * Returns time in a human-readable format
     * @param timeType type of time (prep/cook/sum)
     * @return a string representing the time in a human-readable format
     */
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

    /**
     * Returns the string representation of this
     * class, with its ingredients and steps
     * serialized.
     *
     * @return string representation of this class
     */
    @Override
    public String toString() {
        StringBuilder stepDescriptionsString = new StringBuilder("[");
        for (String stepDescription : stepDescriptions)
            stepDescriptionsString.append("Step('").append(stepDescription).append("'), ");
        stepDescriptionsString.append("]");

        StringBuilder ingredientsString = new StringBuilder("[");
        for (Ingredient ingredient : ingredients)
            ingredientsString.append(ingredient.toString()).append(", ");
        ingredientsString.append("]");

        return "Recipe{" +
                "serverId='" + serverId + '\'' +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fullCookTime=" + fullCookTime +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", cuisine='" + cuisine + '\'' +
                ", cookedByUser=" + cookedByUser +
                ", isSaved=" + isSaved +
                ", createdByUser=" + createdByUser +
                ", nSteps=" + nSteps +
                ", stepDescriptions=" + stepDescriptionsString +
                ", nIngreds=" + nIngreds +
                ", ingredients=" + ingredientsString +
                '}';
    }

    /**
     * Returns a string representation of
     * the list of ingredients.
     * @return string representation of the list of ingredients
     */
    public List<String> getIngredientsStr() {
        ArrayList<String> ret = new ArrayList<>();
        for (Ingredient ingred : ingredients)
            ret.add(ingred.getName());
        return ret;
    }

    // ===============================================================
    // generated getters and setters

    public List<Boolean> getIsIngredChecked() {
        return isIngredChecked;
    }

    public boolean isIngredChecked(int position) {
        return isIngredChecked.get(position);
    }

    public void setIngredChecked(int position, boolean checked) {
        isIngredChecked.set(position, checked);
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

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
