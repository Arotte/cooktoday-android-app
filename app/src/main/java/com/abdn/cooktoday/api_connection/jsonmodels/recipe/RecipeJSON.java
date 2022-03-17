package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.local_data.model.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeJSON {
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
    private ArrayList<InstructionJSON> instructions;
    private ArrayList<String> ingredients;
    private int calories;

    public RecipeJSON(String id, String name, String shortDesc, int cookingTime, int prepTime, int portionsNum, String dateOfCreation, String authorId, ArrayList<String> media, ArrayList<InstructionJSON> instructions, ArrayList<String> ingredients, int calories, String longDesc) {
        this.id = id;
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

    public ArrayList<InstructionJSON> getInstructions() {
        return instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public ArrayList<String> getInstructionsStr() {
        ArrayList<String> ret = new ArrayList<>();
        for (InstructionJSON instructionJson : instructions) {
            ret.add(instructionJson.getText());
        }
        return ret;
    }

    public ArrayList<Ingredient> getIngredientsIngred() {
        ArrayList<Ingredient> ret = new ArrayList<>();
        for (String ingredStr : ingredients) {
            ret.add(new Ingredient(ingredStr, ""));
        }
        return ret;
    }
}
