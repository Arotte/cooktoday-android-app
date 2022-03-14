package com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe;

public class ExtractedRecipeNutritionJSON {
    private String calories;
    private String carbohydrateContent;
    private String proteinContent;
    private String fatContent;
    private String saturatedFatContent;
    private String transFatContent;
    private String cholesterolContent;
    private String sodiumContent;
    private String fiberContent;
    private String sugarContent;
    private String servingSize;

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
