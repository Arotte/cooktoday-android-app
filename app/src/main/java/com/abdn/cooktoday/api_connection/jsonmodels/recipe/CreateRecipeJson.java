package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreateIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;
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
    private ArrayList<CreateRecipeIngredientJson> ingredients;
    private int calories;
    private String cuisine;

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
            this.cuisine = "none";
        else
            this.cuisine = recipe.getCuisine();

        this.instructions = new ArrayList<>();
        for (String stepStr : recipe.getStepDescriptions())
            this.instructions.add(new CreatedInstructionJson(stepStr, new ArrayList<String>()));

        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getId() == null || ingredient.getId().isEmpty()) {
                // this ingredient is not saved in the database, so
                // we need to create a new one
                Server.createNewIngredient(LoggedInUser.user().getSessionID(), new CreateIngredientJson(ingredient), new ServerCallbacks.CreateNewIngredientCallback() {
                    @Override
                    public void success(IngredientJson ingredientJson) {
                        // ingredient successfully created on the server
                        ingredient.setId(ingredientJson.get_id());
                        ingredients.add(new CreateRecipeIngredientJson(ingredient));
                    }

                    @Override
                    public void error(int errorCode) {
                        // error creating ingredient on the server
                    }
                });
            } else {
                this.ingredients.add(new CreateRecipeIngredientJson(ingredient));
            }
        }
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
