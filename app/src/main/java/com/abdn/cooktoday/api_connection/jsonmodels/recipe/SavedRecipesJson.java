package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import java.util.List;

public class SavedRecipesJson {
    private List<RecipeJson> recipes;

    public SavedRecipesJson(List<RecipeJson> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeJson> getRecipes() {
        return recipes;
    }
}
