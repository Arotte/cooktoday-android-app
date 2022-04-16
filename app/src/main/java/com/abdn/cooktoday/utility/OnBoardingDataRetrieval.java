package com.abdn.cooktoday.utility;

import android.content.Intent;
import android.util.Log;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.onboarding.OnBoardingActivity;

import java.util.List;

public class OnBoardingDataRetrieval {

    public interface RetrievalResult {
        void success();
        void error(int where, String whereStr, int errorCode);
    }

    /**
     * Retrieve data from server.
     *
     * 1. Load user's saved recipes from server.
     * 2. Load recommended recipes from server.
     * 3. Call success callback.
     */
    public static void retrieve(String logTAG, RetrievalResult resultCallback) {
        // 1.) retrieve saved recipes from server, and save them locally
        Server.getAllSavedRecipes(LoggedInUser.user().getSessionID(), new Server.GetSavedRecipesResult() {
            @Override
            public void success(List<Recipe> recipes) {
                Log.i(logTAG, "Saved recipes successfully retrieved from server!");
                LoggedInUser.user().setSavedRecipes(recipes);

                // 2.) retrieve recommended recipes from server, and save them
                Server.getRecommendedRecipes(LoggedInUser.user().getSessionID(), new Server.GetRecommendedRecipesResult() {
                    @Override
                    public void success(List<Recipe> recommendedRecipes) {
                        Log.i(logTAG, "Successfully retrieved recommended recipes from server!");
                        LoggedInUser.user().setRecommendedRecipes(recommendedRecipes);

                        // 3.) call success result callback
                        resultCallback.success();
                    }
                    @Override
                    public void error(int errorCode) {
                        Log.i(logTAG, "Error (" + errorCode + ") while retrieving recommended recipes from server!");
                        resultCallback.error(2, "Getting recommended recipes from server", errorCode);
                    }
                });
            }
            @Override
            public void error(int errorCode) {
                Log.i(logTAG, "Error while querying saved recipes from server (code: " + errorCode + ")!");
                resultCallback.error(1, "Getting saved recipes from server", errorCode);
            }
        });
    }
}
