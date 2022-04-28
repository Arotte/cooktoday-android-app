package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.api_connection.jsonmodels.UserPrefsJsonModel;
import com.abdn.cooktoday.api_connection.jsonmodels.feed.HomeFeedJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredSearchResultItemJson;
import com.abdn.cooktoday.api_connection.jsonmodels.ingredient.IngredientJson;
import com.abdn.cooktoday.api_connection.jsonmodels.media.AwsUploadedFilesJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe.CreateRecipeJson;
import com.abdn.cooktoday.api_connection.jsonmodels.recipe_search.RecipeSearchResultItemJSON;
import com.abdn.cooktoday.local_data.model.NerredIngred;
import com.abdn.cooktoday.local_data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerCallbacks
 *
 * This meta-interface is used to define the
 * callback methods that will be used by the
 * main CookToday API connection class.
 */
public interface ServerCallbacks {
    /*
    =============================================
    RESULT CALLBACKS
    ============================================= */

    interface RecipeExtractionResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    interface RecipeSearchResult {
        void success(ArrayList<RecipeSearchResultItemJSON> recipes);
        void error(int errorCode);
    }

    interface GetRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    interface CreateRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    interface SaveUserPrefResult {
        void success(UserPrefsJsonModel savedUserPrefs);
        void error(int errorCode);
    }

    interface GetSavedRecipesResult {
        void success(List<Recipe> recipes);
        void error(int errorCode);
    }

    interface SaveRecipeResult {
        void success(Recipe recipe);
        void error(int errorCode);
    }

    interface GetRecommendedRecipesResult {
        void success(List<Recipe> recommendedRecipes);
        void error(int errorCode);
    }

    interface IngredientNerResult {
        void success(NerredIngred ingredient);
        void error(int errorCode);
    }

    interface AwsRecipeImgUploadResult {
        void success(AwsUploadedFilesJson files);
        void error(int errorCode);
    }

    interface ListOfRecipesCallback {
        void success(List<Recipe> recipes);
        void error(int errorCode);
    }

    interface GetIngredientCallback {
        void success(IngredientJson ingredient);
        void error(int errorCode);
    }

    interface CreateNewIngredientCallback {
        void success(IngredientJson ingredient);
        void error(int errorCode, String errorMessage);
    }

    interface IngredSearchCallback {
        void success(List<IngredSearchResultItemJson> ingredients);
        void error(int errorCode);
    }

    interface HomeFeedResultCallback {
        void success(HomeFeedJson homeFeed);
        void error(int errorCode);
    }

    interface CookRecipeCallback {
        void success();
        void error(int errorCode);
    }

    interface CreateRecipeRequestJsonCreatedCallback {
        void onSuccess(CreateRecipeJson json);
        void onFailure(String error);
    }
}
