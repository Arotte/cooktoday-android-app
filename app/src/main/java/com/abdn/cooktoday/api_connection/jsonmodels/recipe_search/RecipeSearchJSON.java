package com.abdn.cooktoday.api_connection.jsonmodels.recipe_search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeSearchJSON {

    @SerializedName("recipes")
    private ArrayList<RecipeSearchResultItemJSON> recipes;

    public RecipeSearchJSON(ArrayList<RecipeSearchResultItemJSON> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<RecipeSearchResultItemJSON> getRecipes() {
        return recipes;
    }
}
