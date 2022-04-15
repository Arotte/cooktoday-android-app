package com.abdn.cooktoday.api_connection.jsonmodels.feed;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendedRecipesJson {
    @SerializedName("recommended_recipes")
    private List<RecipeJSON> recommendedRecipes;

    public RecommendedRecipesJson(List<RecipeJSON> recommendedRecipes) {
        this.recommendedRecipes = recommendedRecipes;
    }

    public List<RecipeJSON> getRecommendedRecipes() {
        return recommendedRecipes;
    }
}
