package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.abdn.cooktoday.local_data.model.Ingredient;

public class CreateRecipeIngredientJson {
    private final String ingredientId;
    private final String quantity;
    private final String unit;
    private final String comment;
    private final String processingMethod;

    public CreateRecipeIngredientJson(String ingredientId, String quantity, String unit, String comment, String processingMethod) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
        this.comment = comment;
        this.processingMethod = processingMethod;
    }

    public CreateRecipeIngredientJson(Ingredient ingredient) {
        this.ingredientId = ingredient.getId();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
        this.comment = ingredient.getComment();
        this.processingMethod = ingredient.getProcessingMethod();
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getComment() {
        return comment;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }
}
