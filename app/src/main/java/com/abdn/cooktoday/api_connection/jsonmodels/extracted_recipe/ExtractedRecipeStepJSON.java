package com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe;

import com.google.gson.annotations.SerializedName;

public class ExtractedRecipeStepJSON {
    @SerializedName("text")
    private final String text;

    @SerializedName("name")
    private final String name;

    @SerializedName("url")
    private final String url;

    public ExtractedRecipeStepJSON(String text, String name, String url) {
        this.text = text;
        this.name = name;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
