package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import java.util.List;

public class IngredSearchJson {
    private final List<IngredSearchResultItemJson> ingredients;

    public IngredSearchJson(List<IngredSearchResultItemJson> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredSearchResultItemJson> getIngredients() {
        return ingredients;
    }
}
