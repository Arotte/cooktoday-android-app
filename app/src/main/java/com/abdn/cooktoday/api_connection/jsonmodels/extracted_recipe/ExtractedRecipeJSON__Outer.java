package com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe;

import com.google.gson.annotations.SerializedName;

public class ExtractedRecipeJSON__Outer {

    @SerializedName("url")
    private String url;

    @SerializedName("recipe")
    private ExtractedRecipeJSON recipe;

    public ExtractedRecipeJSON__Outer(String url, ExtractedRecipeJSON recipe) {
        this.url = url;
        this.recipe = recipe;
    }

    public String getUrl() {
        return url;
    }

    public ExtractedRecipeJSON getRecipe() {
        return recipe;
    }
}
