package com.abdn.cooktoday.api_connection;

import android.util.Log;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreateIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchResultItemJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeIngredientJson;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Ingredient;

import java.util.List;

public class IngredientCreator {
    private static final String TAG = "IngredientCreator";

    private int nIngredCreated;
    private int nIngredTotal;
    private List<Ingredient> ingredientList;

    public interface IngredientsCreatedCallback {
        void onIngredientsCreated(List<Ingredient> ingredients);
        void onIngredientsCreationFailed();
    }

    public IngredientCreator(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        nIngredCreated = 0;
        nIngredTotal = ingredientList.size();
    }

    public void create(IngredientsCreatedCallback callback) {
        rec_create(callback);
    }

    private void rec_create(IngredientsCreatedCallback ingredientsCreatedCallback) {
        Ingredient ingredient = ingredientList.get(nIngredCreated);
        if (ingredient.getId() == null || ingredient.getId().isEmpty()) {
            // this ingredient is not saved in the database, so
            // we need to create a new one
            Server.createNewIngredient(LoggedInUser.user().getSessionID(), new CreateIngredientJson(ingredient), new ServerCallbacks.CreateNewIngredientCallback() {
                @Override
                public void success(IngredientJson ingredientJson) {
                    Log.i(TAG, "Success creating ingredient: " + ingredientJson.getName());
                    // ingredient successfully created on the server
                    ingredientList.get(nIngredCreated).setId(ingredientJson.get_id());
                    nIngredCreated++;
                    if (nIngredCreated < nIngredTotal)
                        rec_create(ingredientsCreatedCallback);
                    else
                        ingredientsCreatedCallback.onIngredientsCreated(ingredientList);
                }

                @Override
                public void error(int errorCode, String errorMessage) {
                    // error creating ingredient on the server
                    if (errorCode == 400 && errorMessage.contains("already exists")) {
                        Log.i(TAG, "Ingredient already exists on the server, searching for it...");
                        // ingredient already exists on the server, so we can just use it
                        // search for the ingredient in the database
                        Server.searchIngredients(LoggedInUser.user().getSessionID(), ingredient.getName(), new ServerCallbacks.IngredSearchCallback() {
                            @Override
                            public void success(List<IngredSearchResultItemJson> ingredientSearchResults) {
                                Log.i(TAG, "Ingredient found on server: '" + ingredientSearchResults.get(0).getName() + "'");
                                // search successfully performed
                                ingredientList.get(nIngredCreated).setId(ingredientSearchResults.get(0).getId());
                                nIngredCreated++;
                                if (nIngredCreated < nIngredTotal)
                                    rec_create(ingredientsCreatedCallback);
                                else
                                    ingredientsCreatedCallback.onIngredientsCreated(ingredientList);
                            }
                            @Override
                            public void error(int errorCode) {
                                // error while searching
                                Log.e(TAG, "Ingredient '" + ingredient.getName() + "' not found on server (error code: " + errorCode + ")");
                                ingredientsCreatedCallback.onIngredientsCreationFailed();
                            }
                        });
                    }
                }
            });
        } else {
            // this ingredient is already saved in the database, so
            // we can just use it
            nIngredCreated++;
            if (nIngredCreated < nIngredTotal)
                rec_create(ingredientsCreatedCallback);
            else
                ingredientsCreatedCallback.onIngredientsCreated(ingredientList);
        }
    }

}
