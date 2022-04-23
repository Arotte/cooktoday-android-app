package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.ArrayList;
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

    public CreateIngredientJson(Ingredient ingred) {
        this.name = ingred.getName();
        this.defaultUnit = ingred.getDefaultUnit();
        this.description = ingred.getDescription();

        if (ingred.getDiet() != null)
            this.diet = ingred.getDiet();
        else
            this.diet = new ArrayList<>();

        this.protein = ingred.getProtein();
        this.carbs = ingred.getCarbs();
        this.fats = ingred.getFats();
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
