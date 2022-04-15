package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import java.util.List;

public class SavedRecipesJson {
    private List<String> savedRecipes;

    public SavedRecipesJson(List<String> savedRecipes) {
        this.savedRecipes = savedRecipes;
    }

    public List<String> getSavedRecipes() {
        return savedRecipes;
    }
}
