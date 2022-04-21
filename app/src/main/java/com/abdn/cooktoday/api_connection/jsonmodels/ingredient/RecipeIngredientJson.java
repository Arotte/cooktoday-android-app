package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import com.google.gson.annotations.SerializedName;

public class RecipeIngredientJson {
    @SerializedName("_id")
    private String recipeIngredientId;
    private String comment;
    private String quantity;
    private String unit;
    private String processingMethod;
    private IngredientJson ingredient;

    public RecipeIngredientJson(String recipeIngredientId, String comment, String quantity, String unit, String processingMethod, IngredientJson ingredient) {
        this.recipeIngredientId = recipeIngredientId;
        this.comment = comment;
        this.quantity = quantity;
        this.unit = unit;
        this.processingMethod = processingMethod;
        this.ingredient = ingredient;
    }

    public String getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public String getComment() {
        return comment;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public IngredientJson getIngredient() {
        return ingredient;
    }
}
