package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import java.util.List;

public class ListOfRecipesJson {
    private List<RecipeJSON> recipes;

    public ListOfRecipesJson(List<RecipeJSON> recipes) {
        this.recipes = recipes;
    }

    public List<RecipeJSON> getRecipes() {
        return recipes;
    }
}
