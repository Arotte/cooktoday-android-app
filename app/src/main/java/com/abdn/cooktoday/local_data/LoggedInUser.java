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

        List<String> cookedRecipeIds = new ArrayList<>();
        for (Recipe cookedRecipe : cookedRecipes)
           cookedRecipeIds.add(cookedRecipe.getServerId());

        List<String> myRecipeIds = new ArrayList<>();
        for (Recipe myRecipe : myRecipes)
            myRecipeIds.add(myRecipe.getServerId());

        List<String> savedRecipeIds = new ArrayList<>();
        for (Recipe savedRecipe : savedRecipes)
            savedRecipeIds.add(savedRecipe.getServerId());

        for (Recipe savedRecipe : savedRecipes) {
           // if recipe is cooked
           String savedRecipeId = savedRecipe.getServerId();
           if (cookedRecipeIds.contains(savedRecipeId)) {
               savedRecipe.setCookedByUser(true);
               removeByIdFromCooked(savedRecipeId);
           } else
               savedRecipe.setCookedByUser(false);
        }

        for (Recipe recommendedRecipe : recommendedRecipes) {
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

    }
    public int nSavedRecipes() { return this.savedRecipes.size(); }

    /*
    Recommended recipes related functions
     */
    public List<Recipe> getRecommendedRecipes() { return this.recommendedRecipes; }
    public void setRecommendedRecipes(List<Recipe> recipes) { this.recommendedRecipes = recipes; }


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
