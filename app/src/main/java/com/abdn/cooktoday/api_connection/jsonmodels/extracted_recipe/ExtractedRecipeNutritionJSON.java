package com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe;

/**
 * ExtractedRecipeNutritionJSON
 *
 * Data class representing the JSON object
 * returned by the CookToday API after
 * a successful extraction of a recipe.
 *
 * Contains private fields, a full
 * constructor, and getters for each
 * field.
 */
public class ExtractedRecipeNutritionJSON {
    private final String calories;
    private final String carbohydrateContent;
    private final String proteinContent;
    private final String fatContent;
    private final String saturatedFatContent;
    private final String transFatContent;
    private final String cholesterolContent;
    private final String sodiumContent;
    private final String fiberContent;
    private final String sugarContent;
    private final String servingSize;

    public ExtractedRecipeNutritionJSON(String calories, String carbohydrateContent, String proteinContent, String fatContent, String saturatedFatContent, String transFatContent, String cholesterolContent, String sodiumContent, String fiberContent, String sugarContent, String servingSize) {
        this.calories = calories;
        this.carbohydrateContent = carbohydrateContent;
        this.proteinContent = proteinContent;
        this.fatContent = fatContent;
        this.saturatedFatContent = saturatedFatContent;
        this.transFatContent = transFatContent;
        this.cholesterolContent = cholesterolContent;
        this.sodiumContent = sodiumContent;
        this.fiberContent = fiberContent;
        this.sugarContent = sugarContent;
        this.servingSize = servingSize;
    }

    public int getCaloriesInt() {
        return Integer.parseInt(
                calories.replaceAll("[^\\d.]", "")
        );
    }

    public int getServingSizeInt() {
        if (servingSize != null)
            return Integer.parseInt(
                    servingSize.replaceAll("[^\\d.]", ""));

        return -1;
    }

    public String getCalories() {
        return calories;
    }

    public String getCarbohydrateContent() {
        return carbohydrateContent;
    }

    public String getProteinContent() {
        return proteinContent;
    }

    public String getFatContent() {
        return fatContent;
    }

    public String getSaturatedFatContent() {
        return saturatedFatContent;
    }

    public String getTransFatContent() {
        return transFatContent;
    }

    public String getCholesterolContent() {
        return cholesterolContent;
    }

    public String getSodiumContent() {
        return sodiumContent;
    }

    public String getFiberContent() {
        return fiberContent;
    }

    public String getSugarContent() {
        return sugarContent;
    }

    public String getServingSize() {
        return servingSize;
    }
}
