package com.abdn.cooktoday.api_connection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.abdn.cooktoday.api_connection.jsonmodels.UserPrefsJsonModel;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.extracted_recipe.ExtractedRecipeStepJSON;
import com.abdn.cooktoday.api_connection.jsonmodels.feed.HomeFeedJson;
import com.abdn.cooktoday.api_connection.jsonmodels.feed.RecommendedRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreateIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.CreatedIngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.ingred_ner.IngredientNerJson;
import com.abdn.cooktoday.api_connection.jsonmodels.media.AwsUploadedFilesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreatedInstructionJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.ListOfRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJSON__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.RecipeJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.SavedRecipesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchJSON;
import com.abdn.cooktoday.local_data.model.Ingredient;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Server
 *
 * This class is essentially a holder class of static functions
 * that are used to communicate with the CookToday API.
 */
public class Server {
    // TODO: use RxJava for threading : https://github.com/ReactiveX/RxJava

    private static final String TAG = "CookTodayServer";

    private static Executor getExec() {
        return new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };
    }

    /*
    =============================================
    COOK RECIPE
    ============================================= */
    public static void cookRecipe(String userSessId, String recipeId, ServerCallbacks.CookRecipeCallback callback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getRecipeService().cookRecipe(userSessId, recipeId).enqueue(new Callback<RecipeJSON__Outer>() {
                    @Override
                    public void onResponse(Call<RecipeJSON__Outer> call, Response<RecipeJSON__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Recipe cooked successfully");
                            callback.success();
                        } else {
                            callback.error(r.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<RecipeJSON__Outer> call, Throwable t) {
                        Log.i(TAG, t + ", " + t.getMessage());
                        callback.error(-1);
                    }
                });
            }
        });
    }

    /*
    =============================================
    GET HOME FEED
    ============================================= */
    public static void getHomeFeed(String userSessId, ServerCallbacks.HomeFeedResultCallback resultCallback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getFeedService().getHomeFeed(userSessId).enqueue(new Callback<HomeFeedJson>() {
                    @Override
                    public void onResponse(Call<HomeFeedJson> call, Response<HomeFeedJson> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Home feed retrieved successfully");
                            resultCallback.success(r.body());
                        } else {
                            resultCallback.error(r.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<HomeFeedJson> call, Throwable t) {
                        Log.i(TAG, t + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }
    /*
    =============================================
    INGREDIENT SEARCH
    ============================================= */
    public static void searchIngredients(String userSessId, String query, ServerCallbacks.IngredSearchCallback resultCallback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getSearchService().searchIngreds(userSessId, query).enqueue(new Callback<IngredSearchJson>() {
                    @Override
                    public void onResponse(Call<IngredSearchJson> call, Response<IngredSearchJson> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, "Ingredient search successful!");
                            resultCallback.success(r.body().getIngredients());
                        } else {
                            resultCallback.error(r.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<IngredSearchJson> call, Throwable t) {
                        Log.i(TAG, t + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }

    /*
    =============================================
    CREATE NEW INGREDIENT
    ============================================= */
    public static void createNewIngredient(String userSessId, CreateIngredientJson ingredientJson, ServerCallbacks.CreateNewIngredientCallback callback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getIngredientService()
                    .createIngredient(userSessId, ingredientJson)
                    .enqueue(new Callback<CreatedIngredientJson>() {
                        @Override
                        public void onResponse(Call<CreatedIngredientJson> call, Response<CreatedIngredientJson> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Ingredient created successfully");
                                callback.success(r.body().getIngredient());
                            } else {
                                try {
                                    callback.error(r.code(), r.errorBody().string());
                                } catch (IOException e) {
                                    callback.error(r.code(), r.message());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CreatedIngredientJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
                            callback.error(-1, t.getMessage());
                        }
                    });
            }
        });
    }

    /*
    =============================================
    GET INGREDIENT BY ID
    ============================================= */
    public static void getIngredientById(String userSessId, String ingredId, ServerCallbacks.GetIngredientCallback callback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getIngredientService()
                    .getIngredientById(userSessId, ingredId)
                    .enqueue(new Callback<IngredientJson__Outer>() {
                        @Override
                        public void onResponse(Call<IngredientJson__Outer> call, Response<IngredientJson__Outer> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Ingredient '" + ingredId + "' successfully retrieved from server!");
                                callback.success(r.body().getIngredient());
                            } else {
                                callback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<IngredientJson__Outer> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
                            callback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    GET ALL RECIPES CREATED BY USER
    ============================================= */
    public static void getAllOwnRecipes(String userSessId, ServerCallbacks.ListOfRecipesCallback callback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getRecipeService()
                    .getRecipesOfUser(userSessId)
                    .enqueue(new Callback<ListOfRecipesJson>() {
                        @Override
                        public void onResponse(Call<ListOfRecipesJson> call, Response<ListOfRecipesJson> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Recipes created by user successfully retrieved from server!");

                                List<Recipe> recipes = new ArrayList<>();
                                for (RecipeJson recipeJson : r.body().getRecipes())
                                    recipes.add(new Recipe(recipeJson));
                                callback.success(recipes);
                            } else {
                                callback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ListOfRecipesJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
                            callback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    GET ALL COOKED RECIPES BY USER
    ============================================= */
    public static void getAllCookedRecipes(String userSessId, ServerCallbacks.ListOfRecipesCallback callback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getRecipeService()
                    .getRecipesCookedByUser(userSessId)
                    .enqueue(new Callback<ListOfRecipesJson>() {
                        @Override
                        public void onResponse(Call<ListOfRecipesJson> call, Response<ListOfRecipesJson> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Recipes cooked by user successfully retrieved from server!");

                                List<Recipe> recipes = new ArrayList<>();
                                for (RecipeJson recipeJson : r.body().getRecipes()) {
                                    Recipe rec = new Recipe(recipeJson);
                                    rec.setCookedByUser(true);
                                    recipes.add(rec);
                                }
                                callback.success(recipes);
                            } else {
                                callback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ListOfRecipesJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
                            callback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    UPLOAD AN IMAGE TO AWS
    ============================================= */
    public static void uploadRecipeImageToAws(String userSessId, File imgFile, ServerCallbacks.AwsRecipeImgUploadResult resultCallback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("media",imgFile.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"),
                                        imgFile))
                        .build();
                Request request = new Request.Builder()
                        .url( APIEndpoints.getBase() + "media/recipe" )
                        .method("POST", body)
                        .addHeader("Cookie", userSessId)
                        .build();
                try {
                    okhttp3.Call call = client.newCall(request);
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                            Log.i(TAG, "ERROR UPLOADING IMAGE TO AWS ====");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                            if (response.code() == 200) {
                                String responsejsonstr =response.body().string();
                                Log.i(TAG, "SUCCESS UPLOADING IMAGE TO AWS! ====== " + response + " ==== " + responsejsonstr);
                                try {
                                    JSONObject responseJson = new JSONObject(responsejsonstr);
                                    JSONArray fileUrls = responseJson.getJSONArray("files");
                                    resultCallback.success(new AwsUploadedFilesJson(fileUrls));
                                } catch (JSONException e) {
                                    Log.i(TAG, "aaaaaaaaaaaaaaaaaaaa");
                                    e.printStackTrace();
                                }
                            } else {
                                Log.i(TAG, "ERROR UPLOADING IMAGE TO AWS! ====== " + response);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    =============================================
    PERFORM NER ON AN INGREDIENT STRING
    ============================================= */
    public static void performNerOnIngred(String userSessId, String ingredStr, ServerCallbacks.IngredientNerResult resultCallback) {
        Executor regExec = getExec();
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getNerService()
                    .performNerOnIngred(userSessId, ingredStr)
                    .enqueue(new Callback<IngredientNerJson>() {
                        @Override
                        public void onResponse(Call<IngredientNerJson> call, Response<IngredientNerJson> r) {
                            if (r.code() == 200) {
                                Log.i(TAG, "Successfully performed NER on ingredient '" + ingredStr + "'");
                                resultCallback.success(new NerredIngred(r.body()));
                            } else {
                                resultCallback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<IngredientNerJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
                            resultCallback.error(-1);
                        }
                    });
            }
        });
    }

    /*
    =============================================
    GET RECOMMENDED RECIPES
    ============================================= */
    public static void getRecommendedRecipes(String userSessId, ServerCallbacks.GetRecommendedRecipesResult resultCallback) {
        Executor regExec = getExec();
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
                                assert r.body() != null;
                                for (RecipeJson recipeJson : r.body().getRecommendedRecipes())
                                    recommendedRecipes.add(new Recipe(recipeJson));
                                resultCallback.success(recommendedRecipes);
                            } else {
                                resultCallback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<RecommendedRecipesJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
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
    public static void saveRecipe(String userSessId, String recipeId, ServerCallbacks.SaveRecipeResult resultCallback) {
        Executor regExec = getExec();
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
                            Log.i(TAG, t + ", " + t.getMessage());
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
    public static void getAllSavedRecipes(String userSessId, ServerCallbacks.GetSavedRecipesResult resultCallback) {
        Executor regExec = getExec();
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
                                assert savedRecipeIds != null;
                                for (RecipeJson recipeJson : savedRecipeIds.getRecipes()) {
                                    Recipe rec = new Recipe(recipeJson);
                                    rec.setSaved(true);
                                    savedRecipes.add(rec);
                                }
                                resultCallback.success(savedRecipes);
                            } else {
                                resultCallback.error(r.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<SavedRecipesJson> call, Throwable t) {
                            Log.i(TAG, t + ", " + t.getMessage());
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
        ServerCallbacks.SaveUserPrefResult resultCallback) {

        UserPrefsJsonModel userPrefsJson = new UserPrefsJsonModel(
                dislikedIngreds,
                cuisines,
                allergies,
                diet,
                cookingSkill
        );

        Executor regExec = getExec();
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
                            Log.i(TAG, t + ", " + t.getMessage());
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
    public static void createRecipe(String userSessId, String userId, Recipe recipe, ServerCallbacks.CreateRecipeResult resultCallback) {
        ArrayList<CreatedInstructionJson> instructionsJson = new ArrayList<>();
        for (String instruction : recipe.getStepDescriptions())
            instructionsJson.add(new CreatedInstructionJson(instruction, new ArrayList<>()));

        String dateOfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        IngredientCreator ingredientCreator = new IngredientCreator(recipe.getIngredients());
        ingredientCreator.create(new IngredientCreator.IngredientsCreatedCallback() {
            @Override
            public void onIngredientsCreated(List<Ingredient> ingredients) {
                // ingredients created

                recipe.setIngredients(ingredients);
                CreateRecipeJson recipeJson = new CreateRecipeJson(recipe, dateOfCreation, userId);

                Executor regExec = getExec();
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
                                    Log.i(TAG, t + ", " + t.getMessage());
                                    resultCallback.error(-1);
                                }
                            });
                    }
                });
            }

            @Override
            public void onIngredientsCreationFailed() {

            }
        });
    }

    /*
    =============================================
    GET RECIPE BY ID
    ============================================= */
    public static void getRecipeById(String userSessId, String recipeId, ServerCallbacks.GetRecipeResult resultCallback) {
        Executor regExec = getExec();
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
                        Log.i(TAG, t + ", " + t.getMessage());
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
    public static void searchRecipes(String userSessId, String query, ServerCallbacks.RecipeSearchResult resultCallback) {
        Executor regExec = getExec();
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
                        Log.i(TAG, t + ", " + t.getMessage());
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
    public static void extractRecipe(String url, ServerCallbacks.RecipeExtractionResult resultCallback) {
        Executor regExec = getExec();
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
                                    recipeIngredients,
                                    ""); // TODO: add cuisine!!!
                            resultCallback.success(recipe);
                        } else {
                            resultCallback.error(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ExtractedRecipeJSON__Outer> call, Throwable t) {
                        Log.i(TAG, t + ", " + t.getMessage());
                        resultCallback.error(-1);
                    }
                });
            }
        });
    }
}
