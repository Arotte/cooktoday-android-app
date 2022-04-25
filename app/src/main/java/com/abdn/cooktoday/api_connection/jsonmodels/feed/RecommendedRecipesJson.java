package com.abdn.cooktoday.api_connection.jsonmodels.feed;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendedRecipesJson {
    @SerializedName("recommended_recipes")
    private final List<RecipeJson> recommendedRecipes;

    public RecommendedRecipesJson(List<RecipeJson> recommendedRecipes) {
        this.recommendedRecipes = recommendedRecipes;
    }

    public List<RecipeJson> getRecommendedRecipes() {
        return recommendedRecipes;
    }
}
