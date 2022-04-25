package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.RecipeIngredientJson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeJson {
    @SerializedName("_id")
    private final String id;
    private final String name;
    private final String shortDesc;
    private final String longDesc;
    private final int cookingTime;
    private final int prepTime;
    private final int portionsNum;
    private final String dateOfCreation;
    private final String authorId;
    private final ArrayList<String> media;
    private final ArrayList<InstructionJson> instructions;
    private final ArrayList<RecipeIngredientJson> ingredients;
    private final int calories;
    private final String cuisine;

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
