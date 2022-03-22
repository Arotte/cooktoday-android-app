package com.abdn.cooktoday.api_connection;

import android.util.Log;

import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeStepJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreatedInstructionJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.InstructionJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Server {
    private static final String TAG = "CookTodayServer";

    public interface RecipeExtractionResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    public interface RecipeSearchResult {
        void success(ArrayList<RecipeSearchResultItemJSON> recipes);
        void error(int errorCode);
    }

    public interface GetRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    public interface CreateRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    public static void createRecipe(String userSessId, String userId, Recipe recipe, CreateRecipeResult resultCallback) {
        ArrayList<CreatedInstructionJson> instructionsJson = new ArrayList<>();
        for (String instruction : recipe.getStepDescriptions())
            instructionsJson.add(new CreatedInstructionJson(instruction, new ArrayList<>()));


        String dateOfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());;
        CreateRecipeJSON recipeJson = new CreateRecipeJSON(recipe, dateOfCreation, userId);

        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getRecipeService()
                        .createRecipe(userSessId, recipeJson)
                        .enqueue(new Callback<RecipeJSON__Outer>() {
                    @Override
                    public void onResponse(Call<RecipeJSON__Outer> call, Response<RecipeJSON__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Recipe created!");
                            resultCallback.success(recipe);
                        } else {
                            resultCallback.error(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeJSON__Outer> call, Throwable t) {
                        Log.i(TAG, t.toString() + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }

    public static void getRecipeById(String userSessId, String recipeId, GetRecipeResult resultCallback) {
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };

        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getRecipeService().getRecipeById(userSessId, recipeId).enqueue(new Callback<RecipeJSON__Outer>() {
                    @Override
                    public void onResponse(Call<RecipeJSON__Outer> call, Response<RecipeJSON__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Got recipe!");
                            RecipeJSON recipeJson = r.body().getRecipe();
                            String recipeImgUrl = "";
                            if (!recipeJson.getMedia().isEmpty())
                                recipeImgUrl = recipeJson.getMedia().get(0);
                            Recipe recipe = new Recipe(
                                   recipeJson.getName(),
                                   recipeJson.getShortDesc(),
                                   recipeJson.getLongDesc(),
                                   recipeImgUrl,
                                   recipeJson.getPortionsNum(),
                                   recipeJson.getCalories(),
                                   recipeJson.getPrepTime(),
                                   recipeJson.getCookingTime(),
                                   recipeJson.getInstructionsStr(),
                                   recipeJson.getIngredientsIngred());
                            resultCallback.success(recipe);
                        } else {
                            resultCallback.error(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeJSON__Outer> call, Throwable t) {
                        Log.i(TAG, t.toString() + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }

    public static void searchRecipes(String userSessId, String query, RecipeSearchResult resultCallback) {
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };

        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getSearchService().searchRecipes(userSessId, query).enqueue(new Callback<RecipeSearchJSON>() {
                    @Override
                    public void onResponse(Call<RecipeSearchJSON> call, Response<RecipeSearchJSON> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Recipe search successful!");
                            resultCallback.success(r.body().getRecipes());
                        } else {
                            resultCallback.error(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchJSON> call, Throwable t) {
                        Log.i(TAG, t.toString() + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }

    public static void extractRecipe(String url, RecipeExtractionResult resultCallback) {
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }};
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
