package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.CookTodaySettings;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateRecipeJson {
    private static final String TAG = "CreateRecipeJson";

    private final String name;
    private final String shortDesc;
    private final String longDesc;
    private final int cookingTime;
    private final int prepTime;
    private final int portionsNum;
    private final String dateOfCreation;
    private final String authorId;
    private final ArrayList<String> media;
    private final ArrayList<CreatedInstructionJson> instructions;
    private final ArrayList<CreateRecipeIngredientJson> ingredients;
    private final int calories;
    private final String cuisine;

    public CreateRecipeJson(
            String name,
            String shortDesc,
            int cookingTime,
            int prepTime,
            int portionsNum,
            String dateOfCreation,
            String authorId,
            ArrayList<String> media,
            ArrayList<CreatedInstructionJson> instructions,
            ArrayList<CreateRecipeIngredientJson> ingredients,
            int calories,
            String longDesc,
            String cuisine) {
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
        this.calories = recipe.getCalories();
        this.longDesc = recipe.getLongDescription();

        if (recipe.getCuisine() == null || recipe.getCuisine().equals(""))
            this.cuisine = CookTodaySettings.noneStr;
        else
            this.cuisine = recipe.getCuisine();

        this.instructions = new ArrayList<>();
        for (String stepStr : recipe.getStepDescriptions())
            this.instructions.add(new CreatedInstructionJson(stepStr, new ArrayList<String>()));

        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients())
            this.ingredients.add(new CreateRecipeIngredientJson(ingredient));
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

    public ArrayList<CreateRecipeIngredientJson> getIngredients() {
        return ingredients;
    }

    public int getCalories() {
        return calories;
    }
}
