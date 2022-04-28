package com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe;

import java.util.List;

/**
 * ExtractedRecipeJSON
 *
 * Data class modelling the JSON response
 * from our recipe extraction endpoint.
 *
 * Contains private fields, a full
 * constructor, and getters for each
 * field.
 */
public class ExtractedRecipeJSON {
    private final List<String> image;
    private final List<String> recipeIngredient;
    private final List<ExtractedRecipeStepJSON> recipeInstructions;
    private final ExtractedRecipeNutritionJSON nutrition;
    private final String keywords;
    private final List<String> description;
    private final String name;
    private final String cookTime;
    private final String prepTime;
    private final String headline;
    private final List<String> recipeCategory; // not used rn
    private final List<String> recipeCuisine; // not used rn
    private final String recipeYield;

    public ExtractedRecipeJSON(List<String> image, List<String> recipeIngredient, List<ExtractedRecipeStepJSON> recipeInstructions, ExtractedRecipeNutritionJSON nutrition, String keywords, List<String> description, String name, String cookTime, String prepTime, String headline, List<String> recipeCategory, List<String> recipeCuisine, String recipeYield) {
        this.image = image;
        this.recipeIngredient = recipeIngredient;
        this.recipeInstructions = recipeInstructions;
        this.nutrition = nutrition;
        this.keywords = keywords;
        this.description = description;
        this.name = name;
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.headline = headline;
        this.recipeCategory = recipeCategory;
        this.recipeCuisine = recipeCuisine;
        this.recipeYield = recipeYield;
    }

    public String getDescriptionStr() {
        StringBuilder str = new StringBuilder();
        for (String d : this.description)
            str.append(d).append("\n\n");
        return str.toString();
    }

    public int getPrepTimeInt() {
        // PT20M -> 10 minutes
        String number = this.prepTime.replace("PT", "").replace("M", "");
        return Integer.parseInt(number);
    }

    public int getCookTimeInt() {
        // PT20M -> 10 minutes
        String number = this.cookTime.replace("PT", "").replace("M", "");
        return Integer.parseInt(number);
    }

    public int getServingSizeInt() {
        if (recipeYield != null)
            return Integer.parseInt(
                    recipeYield.replaceAll("[^\\d.]", ""));
        return -1;
    }

    public List<String> getImage() {
        return image;
    }

    public List<String> getRecipeIngredient() {
        return recipeIngredient;
    }

    public List<ExtractedRecipeStepJSON> getRecipeInstructions() {
        return recipeInstructions;
    }

    public ExtractedRecipeNutritionJSON getNutrition() {
        return nutrition;
    }

    public String getKeywords() {
        return keywords;
    }

    public List<String> getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getHeadline() {
        return headline;
    }

    public List<String> getRecipeCategory() {
        return recipeCategory;
    }

    public List<String> getRecipeCuisine() {
        return recipeCuisine;
    }

    public String getRecipeYield() {
        return recipeYield;
    }
}
