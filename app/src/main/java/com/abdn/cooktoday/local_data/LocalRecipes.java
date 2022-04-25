package com.abdn.cooktoday.local_data;

import com.abdn.cooktoday.local_data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class LocalRecipes {
    // Singleton setup
    private static LocalRecipes instance;
    private LocalRecipes() {
    }
    public static LocalRecipes i() {
        if (instance == null) {
            instance = new LocalRecipes();
        }
        return instance;
    }

    private List<TypeRecipe> recipes;

    // =============================================================================================
    // Adding/removing recipes
    // =============================================================================================

    public void addRecipe(Recipe recipe, Type type) {
        if (recipes == null)
            recipes = new ArrayList<>();

        if (recipes.contains(recipe)) {
            int index = recipes.indexOf(recipe);
            this.recipes.get(index).addType(type);
        } else {
            recipes.add(new TypeRecipe(recipe, type));
        }
    }

    public void addRecipes(List<Recipe> recipes, Type type) {
        for (Recipe recipe : recipes) {
            addRecipe(recipe, type);
        }
    }

    public void removeById(String id) {
        if (recipes == null) {
            return;
        }
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getRecipe().getServerId().equals(id)) {
                recipes.remove(i);
                return;
            }
        }
    }

    // =============================================================================================
    // Getting recipes
    // =============================================================================================

    public List<Recipe> getSavedRecipes() {
        return getRecipesByType(Type.SAVED);
    }

    public List<Recipe> getRecipesCookedByUser() {
        return getRecipesByType(Type.COOKED_BY_USER);
    }

    public List<Recipe> getRecommendedRecipes() {
        return getRecipesByType(Type.RECOMMENDED);
    }

    public List<Recipe> getPersonalizedRecipes() {
        return getRecipesByType(Type.PERSONALIZED);
    }

    public List<Recipe> getRecipesAddedByUser() {
        return getRecipesByType(Type.ADDED_BY_USER);
    }

    private List<Recipe> getRecipesByType(Type type) {
        if (recipes == null) {
            return new ArrayList<>();
        }
        List<Recipe> recipes = new ArrayList<>();
        for (TypeRecipe typeRecipe : this.recipes) {
            if (typeRecipe.containsType(type)) {
                recipes.add(typeRecipe.getRecipe());
            }
        }
        return recipes;
    }

    // =============================================================================================
    // Setting recipes
    // =============================================================================================

    public void recipeCooked(String id) {
        if (recipes == null)
            return;
        for (TypeRecipe typeRecipe : this.recipes) {
            if (typeRecipe.getRecipe().getServerId().equals(id)) {
                typeRecipe.addType(Type.COOKED_BY_USER);
                return;
            }
        }
    }

    public void recipeSaved(String id) {
        if (recipes == null)
            return;
        for (TypeRecipe typeRecipe : this.recipes) {
            if (typeRecipe.getRecipe().getServerId().equals(id)) {
                typeRecipe.addType(Type.SAVED);
                return;
            }
        }
    }

    // =============================================================================================
    // Checking of recipe ingredients
    // =============================================================================================

    public void setIngredientsChecked(String recipeId, List<Boolean> ingredientStates) {
        if (recipes == null)
            return;
        for (TypeRecipe typeRecipe : this.recipes) {
            if (typeRecipe.getRecipe().getServerId().equals(recipeId)) {
                for (int i = 0; i < ingredientStates.size(); i++)
                    typeRecipe.getRecipe().setIngredChecked(i, ingredientStates.get(i));
                return;
            }
        }
    }

    // =============================================================================================
    // Helper enum and class
    // =============================================================================================

    // Types of local recipes
    public enum Type {
        RECOMMENDED,
        PERSONALIZED,
        SAVED,
        ADDED_BY_USER,
        COOKED_BY_USER
    }

    private class TypeRecipe {
        private final List<Type> types;
        private final Recipe recipe;

        public TypeRecipe(List<Type> types, Recipe recipe) {
            this.types = types;
            this.recipe = recipe;

            for (Type type : types)
                handleNewType(type);
        }

        public TypeRecipe(Recipe recipe, Type type) {
            this.recipe = recipe;
            types = new ArrayList<>();
            types.add(type);
            handleNewType(type);
        }

        public List<Type> getTypes() {
            return types;
        }

        public Recipe getRecipe() {
            return recipe;
        }

        public void addType(Type type) {
            if (!types.contains(type)) {
                types.add(type);
                handleNewType(type);
            }
        }

        public void removeType(Type type) {
            if (types.contains(type)) {
                types.remove(type);
                handleRemovedType(type);
            }
        }

        public boolean containsType(Type type) {
            return types.contains(type);
        }

        private void handleNewType(Type type) {
            if (type == Type.COOKED_BY_USER)
                this.recipe.setCookedByUser(true);
            if (type == Type.ADDED_BY_USER)
                this.recipe.setCreatedByUser(true);
            if (type == Type.SAVED)
                this.recipe.setSaved(true);
        }

        private void handleRemovedType(Type type) {
            if (type == Type.COOKED_BY_USER)
                this.recipe.setCookedByUser(false);
            if (type == Type.ADDED_BY_USER)
                this.recipe.setCreatedByUser(false);
            if (type == Type.SAVED)
                this.recipe.setSaved(false);
        }
    }
}
