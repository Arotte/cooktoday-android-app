package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import java.util.List;

public class ListOfRecipesJson {
    private List<RecipeJson> recipes;

    public ListOfRecipesJson(List<RecipeJson> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeJson> getRecipes() {
        return recipes;
    }
}
