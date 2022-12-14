package com.abdn.cooktoday.api_connection.jsonmodels.ingredient;

import com.abdn.cooktoday.CookTodaySettings;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CreateIngredientJson {
    private final String name;
    private final String defaultUnit;
    private final String description;
    private final List<String> diet;
    private final int protein;
    private final int carbs;
    private final int fats;

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
        if (ingred.getDescription() != null && !ingred.getDescription().isEmpty())
            this.description = ingred.getDescription();
        else
            this.description = CookTodaySettings.noneStr;


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
