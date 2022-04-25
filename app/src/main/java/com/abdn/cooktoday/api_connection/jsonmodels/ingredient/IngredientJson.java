package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import java.util.List;

public class IngredientJson {
    private final String _id;
    private final String name;
    private final String defaultUnit;
    private final String description;
    private final List<String> diet;
    private final int protein;
    private final int carbs;
    private final int fats;

    public IngredientJson(String _id, String name, String defaultUnit, String description, List<String> diet, int protein, int carbs, int fats) {
        this._id = _id;
        this.name = name;
        this.defaultUnit = defaultUnit;
        this.description = description;
        this.diet = diet;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDiet() {
        return diet;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFats() {
        return fats;
    }
}
