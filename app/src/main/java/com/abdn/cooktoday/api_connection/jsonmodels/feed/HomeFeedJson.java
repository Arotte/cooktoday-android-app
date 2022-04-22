package com.abdn.cooktoday.api_connection.jsonmodels.feed;

import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJson;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeFeedJson {
    private List<String> categories;
    @SerializedName("recommended_recipes")
    private List<RecipeJson> recommendedRecipes;
    @SerializedName("personalized_recipes")
    private List<RecipeJson> personalizedRecipes;

    public HomeFeedJson(List<String> categories, List<RecipeJson> recommendedRecipes, List<RecipeJson> personalizedRecipes) {
        this.categories = categories;
        this.recommendedRecipes = recommendedRecipes;
        this.personalizedRecipes = personalizedRecipes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<RecipeJson> getRecommendedRecipes() {
        return recommendedRecipes;
    }

    public List<RecipeJson> getPersonalizedRecipes() {
        return recommendedRecipes;
    }

    public List<Recipe> getRecommendedRecipesInternal() {
        List<Recipe> ret = new ArrayList<>();
        for (RecipeJson r : recommendedRecipes) {
            ret.add(new Recipe(r));
        }
        return ret;
    }

    public List<Recipe> getPersonalizedRecipesInternal() {
        List<Recipe> ret = new ArrayList<>();
        for (RecipeJson r : personalizedRecipes) {
            ret.add(new Recipe(r));
        }
        return ret;
    }
}
