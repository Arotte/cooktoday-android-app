package com.abdn.cooktoday.local_data;

import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.local_data.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoggedInUser {
    private static LoggedInUser instance;
    private LoggedInUser() {
        fistName = "";
        lastName = "";
        serverID = "";
        sessionID = "";
        role = "";
        email = "";
    }
    public static LoggedInUser user() {
        if (instance == null)
            instance = new LoggedInUser();
        return instance;
    }

    private String fistName;
    private String lastName;
    private String serverID;
    private String sessionID;
    private String role;
    private String email;
    private List<Recipe> savedRecipes;
    private List<Recipe> recommendedRecipes;
    private List<Recipe> myRecipes;
    private List<Recipe> cookedRecipes;
    private List<Recipe> personalizedRecipes;

    private List<String> recommendedRecipeIds;
    private List<String> myRecipeIds;
    private List<String> cookedRecipeIds;

    private List<String> homeFeedCategories;

    public void setUser(User user) {
        fistName = user.getFirstName();
        lastName = user.getLastName();
        role = user.getRole();
        email = user.getEmail();
        serverID = user.getServerId();
        sessionID = user.getSessionId();
    }

    /**
     *
     */
    public void normalizeRecipes() {
        if (savedRecipes == null || recommendedRecipes == null || myRecipes == null || cookedRecipes == null)
           return;

        // -----------------

        cookedRecipeIds = new ArrayList<>();
        for (Recipe cookedRecipe : cookedRecipes)
           cookedRecipeIds.add(cookedRecipe.getServerId());

        myRecipeIds = new ArrayList<>();
        for (Recipe myRecipe : myRecipes)
            myRecipeIds.add(myRecipe.getServerId());

        List<String> savedRecipeIds = new ArrayList<>();
        for (Recipe savedRecipe : savedRecipes)
            savedRecipeIds.add(savedRecipe.getServerId());

        for (Recipe savedRecipe : savedRecipes) {
            savedRecipe.setCookedByUser(cookedRecipeIds.contains(savedRecipe.getServerId()));
        }

        for (Recipe recommendedRecipe : recommendedRecipes) {
            recommendedRecipe.setCreatedByUser(myRecipeIds.contains(recommendedRecipe.getServerId()));
            recommendedRecipe.setCookedByUser(cookedRecipeIds.contains(recommendedRecipe.getServerId()));
            recommendedRecipe.setSaved(savedRecipeIds.contains(recommendedRecipe.getServerId()));
        }

        for (Recipe myRecipe : myRecipes) {
           myRecipe.setCookedByUser(cookedRecipeIds.contains(myRecipe.getServerId()));
           String myRecipeId = myRecipe.getServerId();
           if (cookedRecipeIds.contains(myRecipeId)) {
               myRecipe.setCookedByUser(true);
               removeByIdFromCooked(myRecipeId);
           } else
               myRecipe.setCookedByUser(false);
        }

        // -----------------


    }

    public void addCookedRecipe(Recipe newCookedRecipe) {
        if (cookedRecipes == null)
            cookedRecipes = new ArrayList<>();
        cookedRecipes.add(newCookedRecipe);
        normalizeRecipes();
    }

    private void removeByIdFromCooked(String recipeId) {
        Recipe target = null;
        for (Recipe rec : cookedRecipes)
            if (rec.getServerId().equals(recipeId)) {
                target = rec;
                break;
            }
        if (target != null)
            cookedRecipes.remove(target);
    }

    /*
    Saved recipe related functions
     */
    public void setSavedRecipes(List<Recipe> recipes) { this.savedRecipes = recipes; }
    public Recipe getSavedRecipe(int idx) { return this.savedRecipes.get(idx); }
    public List<Recipe> getSavedRecipes() { return this.savedRecipes; }
    public void addSavedRecipe(Recipe newRecipe) {
        this.savedRecipes.add(newRecipe);
        notifySaved(newRecipe.getServerId());
    }
    public int nSavedRecipes() { return this.savedRecipes.size(); }

    private void notifySaved(String recipeId) {
        if (this.recommendedRecipeIds.contains(recipeId))
            if (this.recommendedRecipes != null)
                this.recommendedRecipes.get(this.recommendedRecipeIds.indexOf(recipeId)).setSaved(true);

        if (this.myRecipeIds.contains(recipeId))
            if (this.myRecipes != null)
                this.myRecipes.get(this.myRecipeIds.indexOf(recipeId)).setSaved(true);

        if (this.cookedRecipeIds.contains(recipeId))
            if (this.cookedRecipes != null)
                this.cookedRecipes.get(this.cookedRecipeIds.indexOf(recipeId)).setSaved(true);
    }

    public List<Recipe> getMyRecipes() {
        return this.myRecipes;
    }

    public List<Recipe> getPersonalizedRecipes() {
        if (this.personalizedRecipes == null)
            return new ArrayList<>();
        return this.personalizedRecipes;
    }

    public void setPersonalizedRecipes(List<Recipe> personalizedRecipes) {
        this.personalizedRecipes = personalizedRecipes;
    }

    public List<String> getHomeFeedCategories() {
        return homeFeedCategories;
    }

    public void setHomeFeedCategories(List<String> homeFeedCategories) {
        this.homeFeedCategories = homeFeedCategories;
    }

    /*
            Recommended recipes related functions
             */
    public List<Recipe> getRecommendedRecipes() { return this.recommendedRecipes; }
    public void setRecommendedRecipes(List<Recipe> recipes) {
        this.recommendedRecipes = recipes;
        this.recommendedRecipeIds = new ArrayList<>();
        for (Recipe recipe : this.recommendedRecipes)
            this.recommendedRecipeIds.add(recipe.getServerId());
    }

    public void newRecipeCreatedByUser(Recipe newRecipe) {
        newRecipe.setCreatedByUser(true);
        this.myRecipes.add(newRecipe);
        this.myRecipeIds.add(newRecipe.getServerId());
        normalizeRecipes();
        // notifySaved(newRecipe.getServerId());
    }


    /*
    Own & cooked recipes related functions
     */
    public void setMyRecipes(List<Recipe> recipes) { this.myRecipes = recipes; }
    public void setCookedRecipes(List<Recipe> cookedRecipes) {
        this.cookedRecipes = cookedRecipes;
    }


    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
