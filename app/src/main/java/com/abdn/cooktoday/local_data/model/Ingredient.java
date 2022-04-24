package com.abdn.cooktoday.local_data.model;

import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.RecipeIngredientJson;

import java.io.Serializable;
import java.util.List;

public class Ingredient implements Serializable {
    // ============================================================
    // fields
    private String id;
    private String name;
    private String quantity;
    private String unit;
    private String comment;
    private String processingMethod;
    private String defaultUnit;
    private String description;
    private List<String> diet;
    private int protein;
    private int carbs;
    private int fats;

    // ============================================================
    // constructors

    public Ingredient() {}

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Ingredient(RecipeIngredientJson recipeIngredientJson) {
        if (recipeIngredientJson != null) {
            this.quantity = recipeIngredientJson.getQuantity();
            this.unit = recipeIngredientJson.getUnit();
            this.comment = recipeIngredientJson.getComment();
            this.processingMethod = recipeIngredientJson.getProcessingMethod();

            IngredientJson ingredientJson = recipeIngredientJson.getIngredient();
            if (ingredientJson != null) {
                this.id = ingredientJson.get_id();
                this.name = ingredientJson.getName();
                this.defaultUnit = ingredientJson.getDefaultUnit();
                this.description = ingredientJson.getDescription();
                this.diet = ingredientJson.getDiet();
                this.protein = ingredientJson.getProtein();
                this.carbs = ingredientJson.getCarbs();
                this.fats = ingredientJson.getFats();
            }
        }
    }

    public Ingredient(IngredientJson ingredientJson) {
        if (ingredientJson != null) {
            this.id = ingredientJson.get_id();
            this.name = ingredientJson.getName();
            this.defaultUnit = ingredientJson.getDefaultUnit();
            this.description = ingredientJson.getDescription();
            this.diet = ingredientJson.getDiet();
            this.protein = ingredientJson.getProtein();
            this.carbs = ingredientJson.getCarbs();
            this.fats = ingredientJson.getFats();
        }
    }

    public void update(NerredIngred nerredIngred) {
        this.quantity = nerredIngred.getQuantity();
        this.unit = nerredIngred.getUnit();
        this.comment = nerredIngred.getNil();
        this.defaultUnit = this.unit;

        if (this.quantity != null)
            this.quantity = this.quantity.replaceAll("[^\\d.]", "");
        if (this.quantity == null || this.quantity.isEmpty())
            this.quantity = "-1";

        if (this.unit == null || this.unit.isEmpty())
            this.unit = "none";
        if (this.comment == null || this.comment.isEmpty())
            this.comment = "none";
        if (this.defaultUnit == null || this.defaultUnit.isEmpty())
            this.defaultUnit = "none";

    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", comment='" + comment + '\'' +
                ", processingMethod='" + processingMethod + '\'' +
                ", defaultUnit='" + defaultUnit + '\'' +
                ", description='" + description + '\'' +
                ", diet=" + diet +
                ", protein=" + protein +
                ", carbs=" + carbs +
                ", fats=" + fats +
                '}';
    }

    // ============================================================
    // default getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(String processingMethod) {
        this.processingMethod = processingMethod;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(String defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDiet() {
        return diet;
    }

    public void setDiet(List<String> diet) {
        this.diet = diet;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }
}
