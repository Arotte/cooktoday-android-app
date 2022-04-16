package com.abdn.cooktoday.api_connection.jsonmodels.ingred_ner;

public class IngredientNerJson {

    private String original;
    private IngredNerTagsJson tags;

    public IngredientNerJson(String original, IngredNerTagsJson tags) {
        this.original = original;
        this.tags = tags;
    }

    public String getOriginal() {
        return original;
    }

    public IngredNerTagsJson getTags() {
        return tags;
    }
}
