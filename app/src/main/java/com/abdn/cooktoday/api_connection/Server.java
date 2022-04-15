package com.abdn.cooktoday.api_connection;

import android.util.Log;

import com.abdn.cooktoday.api_connection.jsonmodels.UserPrefsJsonModel;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeStepJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.feed.RecommendedRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreatedInstructionJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.InstructionJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.SavedRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.local_data.model.User;

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

    /*
    =============================================
    RESULT CALLBACKS
    ============================================= */

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

    public interface SaveUserPrefResult {
        void success(UserPrefsJsonModel savedUserPrefs);
        void error(int errorCode);
    }

    public interface GetSavedRecipesResult {
        void success(List<Recipe> recipes);
        void error(int errorCode);
    }

    public interface SaveRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    public interface GetRecommendedRecipesResult {
        void success(List<Recipe> recommendedRecipes);
        void error(int errorCode);
    }

    /*
    =============================================
    GET RECOMMENDED RECIPES
    ============================================= */
    public static void getRecommendedRecipes(String userSessId, GetRecommendedRecipesResult resultCallback) {
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };

        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getFeedService()
                    .getRecommendedRecipes(userSessId)
                    .enqueue(new Callback<RecommendedRecipesJson>() {
                        @Override
                        public void onResponse(Call<RecommendedRecipesJson> call, Response<RecommendedRecipesJson> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Successfully retrieved recommended recipes!");
                                List<Recipe> recommendedRecipes = new ArrayList<>();
                                for (RecipeJSON recipeJson : r.body().getRecommendedRecipes())
                                    recommendedRecipes.add(new Recipe(recipeJson));
                                resultCallback.success(recommendedRecipes);
                            } else {
                                resultCallback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<RecommendedRecipesJson> call, Throwable t) {
                            Log.i(TAG, t.toString() + ", " + t.getMessage());
                            resultCallback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    SAVE A RECIPE
    ============================================= */
    public static void saveRecipe(String userSessId, String recipeId, SaveRecipeResult resultCallback) {
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
                    .saveRecipe(userSessId, recipeId)
                    .enqueue(new Callback<RecipeJSON__Outer>() {
                        @Override
                        public void onResponse(Call<RecipeJSON__Outer> call, Response<RecipeJSON__Outer> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Recipe " + recipeId + " successfully saved for user!");
                                resultCallback.success(new Recipe(r.body().getRecipe()));
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

    /*
    =============================================
    GET ALL SAVED RECIPES OF USER
    ============================================= */
    public static void getAllSavedRecipes(String userSessId, GetSavedRecipesResult resultCallback) {
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
                        .getSavedRecipes(userSessId)
                        .enqueue(new Callback<SavedRecipesJson>() {
                            @Override
                            public void onResponse(Call<SavedRecipesJson> call, Response<SavedRecipesJson> r) {
                                if (r.code() == 200) {
                                    Log.i(TAG, "Saved recipe IDs retrieved!");
                                    SavedRecipesJson savedRecipeIds = r.body();
                                    List<Recipe> savedRecipes = new ArrayList<>();

                                    // get recipes by ID from server one by one
                                    for (String savedRecipeId : savedRecipeIds.getSavedRecipes()) {
                                        getRecipeById(userSessId, savedRecipeId, new GetRecipeResult() {
                                            @Override
                                            public void success(Recipe recipe) {
                                                Log.i(TAG, "Recipe " + savedRecipeId + " successfully retrieved from server!");
                                                savedRecipes.add(recipe);
                                            }
                                            @Override
                                            public void error(int errorCode) {
                                                Log.i(TAG, "Error retrieving recipe " + savedRecipeId + " from server (code: " + errorCode + ")!");
                                            }
                                        });
                                    }

                                    resultCallback.success(savedRecipes);
                                } else {
                                    resultCallback.error(r.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<SavedRecipesJson> call, Throwable t) {
                                Log.i(TAG, t.toString() + ", " + t.getMessage());
                                resultCallback.error(-1);
                            }
                        });
            }
        });
    }

    /*
    =============================================
    SAVE USER PREFERENCES
    ============================================= */
    public static void saveUserPrefs(
        String userSessId,
        List<String> dislikedIngreds,
        List<String> cuisines,
        List<String> allergies,
        List<String> diet,
        String cookingSkill,
        SaveUserPrefResult resultCallback) {

        UserPrefsJsonModel userPrefsJson = new UserPrefsJsonModel(
                dislikedIngreds,
                cuisines,
                allergies,
                diet,
                cookingSkill
        );

        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };

        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getUserService()
                    .saveUserPrefs(userSessId, userPrefsJson)
                    .enqueue(new Callback<UserPrefsJsonModel>() {
                        @Override
                        public void onResponse(Call<UserPrefsJsonModel> call, Response<UserPrefsJsonModel> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "User preferences saved!");
                                resultCallback.success(r.body());
                            } else {
                                resultCallback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserPrefsJsonModel> call, Throwable t) {
                            Log.i(TAG, t.toString() + ", " + t.getMessage());
                            resultCallback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    CREATE NEW RECIPE
    ============================================= */
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

    /*
    =============================================
    GET RECIPE BY ID
    ============================================= */
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
                            resultCallback.success(new Recipe(r.body().getRecipe()));
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

    /*
    =============================================
    SEARCH RECIPES
    ============================================= */
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

    /*
    =============================================
    EXTRACT RECIPE FROM URL
    ============================================= */
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
