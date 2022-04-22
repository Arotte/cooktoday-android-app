package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.RecipeIngredientJson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeJson {
    @SerializedName("_id")
    private String id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private int cookingTime;
    private int prepTime;
    private int portionsNum;
    private String dateOfCreation;
    private String authorId;
    private ArrayList<String> media;
    private ArrayList<InstructionJson> instructions;
    private ArrayList<RecipeIngredientJson> ingredients;
    private int calories;
    private String cuisine;

    public RecipeJson(String id, String name, String shortDesc, String longDesc, int cookingTime, int prepTime, int portionsNum, String dateOfCreation, String authorId, ArrayList<String> media, ArrayList<InstructionJson> instructions, ArrayList<RecipeIngredientJson> ingredients, int calories, String cuisine) {
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.cookingTime = cookingTime;
        this.prepTime = prepTime;
        this.portionsNum = portionsNum;
        this.dateOfCreation = dateOfCreation;
        this.authorId = authorId;
        this.media = media;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.calories = calories;
        this.cuisine = cuisine;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public String getId() {
        return id;
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

    public ArrayList<InstructionJson> getInstructions() {
        return instructions;
    }

    public ArrayList<RecipeIngredientJson> getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public String getCuisine() {
        return cuisine;
    }

    public ArrayList<String> getInstructionsStr() {
        ArrayList<String> ret = new ArrayList<>();
        for (InstructionJson instructionJson : instructions) {
            if (instructionJson != null)
                ret.add(instructionJson.getText());
        }
        return ret;
    }
}
