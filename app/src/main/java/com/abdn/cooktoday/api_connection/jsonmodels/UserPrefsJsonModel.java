package com.abdn.cooktoday.api_connection.jsonmodels;

import java.util.List;

public class UserPrefsJsonModel {
    private List<String> dislikedIngreds;
    private List<String> cuisines;
    private List<String> allergies;
    private List<String> diet;
    private String cookingSkill;

    public UserPrefsJsonModel(List<String> dislikedIngreds, List<String> cuisines, List<String> allergies, List<String> diet, String cookingSkill) {
        this.dislikedIngreds = dislikedIngreds;
        this.cuisines = cuisines;
        this.allergies = allergies;
        this.diet = diet;
        this.cookingSkill = cookingSkill;
    }

    public List<String> getDislikedIngreds() {
        return dislikedIngreds;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<String> getDiet() {
        return diet;
    }

    public String getCookingSkill() {
        return cookingSkill;
    }
}
