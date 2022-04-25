package com.abdn.cooktoday.api_connection.jsonmodels.recipe_search;

import com.google.gson.annotations.SerializedName;

public class RecipeSearchResultItemJSON {

    @SerializedName("_id")
    private final String id;
    private final String name;
    private final String shortDesc;
    private final double score;

    public RecipeSearchResultItemJSON(String id, String name, String shortDesc, int score) {
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public double getScore() {
        return score;
    }
}
