package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import java.util.List;

public class CreatedInstructionJson {
    private final String text;
    private final List<String> ingredients;

    public CreatedInstructionJson(String text, List<String> ingredients) {
        this.text = text;
        this.ingredients = ingredients;
    }

    public String getText() {
        return text;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
