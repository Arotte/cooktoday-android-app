package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import java.util.List;

public class CreateIngredientJson {
    private String name;
    private String defaultUnit;
    private String description;
    private List<String> diet;
    private int protein;
    private int carbs;
    private int fats;

    public CreateIngredientJson(String name, String defaultUnit, String description, List<String> diet, int protein, int carbs, int fats) {
        this.name = name;
        this.defaultUnit = defaultUnit;
        this.description = description;
        this.diet = diet;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
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
