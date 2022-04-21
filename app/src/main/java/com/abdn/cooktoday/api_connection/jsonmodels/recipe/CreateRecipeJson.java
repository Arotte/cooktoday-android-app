package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.local_data.model.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateRecipeJson {
    private String name;
    private String shortDesc;
    private String longDesc;
    private int cookingTime;
    private int prepTime;
    private int portionsNum;
    private String dateOfCreation;
    private String authorId;
    private ArrayList<String> media;
    private ArrayList<CreatedInstructionJson> instructions;
    private ArrayList<String> ingredients;
    private int calories;
    private String cuisine;

    public CreateRecipeJson(String name, String shortDesc, int cookingTime, int prepTime, int portionsNum, String dateOfCreation, String authorId, ArrayList<String> media, ArrayList<CreatedInstructionJson> instructions, ArrayList<String> ingredients, int calories, String longDesc, String cuisine) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.cookingTime = cookingTime;
        this.prepTime = prepTime;
        this.portionsNum = portionsNum;
        this.dateOfCreation = dateOfCreation;
        this.authorId = authorId;
        this.media = media;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.calories = calories;
        this.longDesc = longDesc;
        this.cuisine = cuisine;
    }
    public CreateRecipeJson(Recipe recipe, String dateOfCreation, String authorId) {
        this.dateOfCreation = dateOfCreation;
        this.name = recipe.getName();
        this.shortDesc = recipe.getShortDescription();
        this.cookingTime = recipe.getCookTime();
        this.prepTime = recipe.getPrepTime();
        this.portionsNum = recipe.getServings();
        this.authorId = authorId;
        this.media = new ArrayList<>(Arrays.asList(recipe.getImgUrl()));
        this.ingredients = (ArrayList<String>) recipe.getIngredientsStr();
        this.calories = recipe.getCalories();
        this.longDesc = recipe.getLongDescription();
        this.cuisine = "test123"; // TODO!!!!!!!

        this.instructions = new ArrayList<>();
        for (String stepStr : recipe.getStepDescriptions())
            this.instructions.add(new CreatedInstructionJson(stepStr, new ArrayList<String>()));
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public String getName() {
        return name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getPortionsNum() {
        return portionsNum;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getAuthorId() {
        return authorId;
    }

    public ArrayList<String> getMedia() {
        return media;
    }

    public ArrayList<CreatedInstructionJson> getInstructions() {
        return instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }
}
