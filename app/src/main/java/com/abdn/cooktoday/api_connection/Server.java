package com.abdn.cooktoday.api_connection;

import android.util.Log;

import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeStepJSON;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Server {
    private static final String TAG = "CookTodayServer";

    public interface RecipeExtractionResult {
        public void success(Recipe recipe);
        public void error(int errorCode);
    }

    public static void extractRecipe(String url, RecipeExtractionResult resultCallback) {
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getExtractedRecipeService().extract(url).enqueue(new Callback<ExtractedRecipeJSON__Outer>() {
                    @Override
                    public void onResponse(Call<ExtractedRecipeJSON__Outer> call, Response<ExtractedRecipeJSON__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Recipe successfully extracted!");
                            ExtractedRecipeJSON recipeJson = r.body().getRecipe();

                            List<String> recipeSteps = new ArrayList<>();
                            for (ExtractedRecipeStepJSON stepJSON : recipeJson.getRecipeInstructions())
                                recipeSteps.add(stepJSON.getText());

                            List<Ingredient> recipeIngredients = new ArrayList<>();
                            for (String ingredStr : recipeJson.getRecipeIngredient())
                                recipeIngredients.add(
                                        new Ingredient(
                                                ingredStr, ""));

                            Recipe recipe = new Recipe(
                                    recipeJson.getName(),
                                    recipeJson.getHeadline(),
                                    recipeJson.getDescriptionStr(),
                                    recipeJson.getImage().get(0),
                                    recipeJson.getServingSizeInt(),
                                    recipeJson.getNutrition().getCaloriesInt(),
                                    recipeJson.getPrepTimeInt(),
                                    recipeJson.getCookTimeInt(),
                                    recipeSteps,
                                    recipeIngredients);
                            resultCallback.success(recipe);
                        } else {
                            resultCallback.error(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ExtractedRecipeJSON__Outer> call, Throwable t) {
                        Log.i(TAG, t.toString() + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }
}
