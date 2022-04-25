package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import com.google.gson.annotations.SerializedName;

public class IngredSearchResultItemJson {
    @SerializedName("_id")
    private final String id;
    private final String name;
    private final String description;
    private final double score;

    public IngredSearchResultItemJson(String id, String name, String description, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getScore() {
        return score;
    }
}
