package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

public class CreatedIngredientJson {
    private IngredientJson ingredient;

    public CreatedIngredientJson(IngredientJson ingredient) {
        this.ingredient = ingredient;
    }

    public IngredientJson getIngredient() {
        return ingredient;
    }
}
