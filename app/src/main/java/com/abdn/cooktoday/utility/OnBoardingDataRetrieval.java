package com.abdn.cooktoday.utility;

import android.util.Log;

import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.api_connection.ServerCallbacks;
import com.abdn.cooktoday.api_connection.jsonmodels.feed.HomeFeedJson;
import com.abdn.cooktoday.local_data.LocalRecipes;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Recipe;

import java.util.List;

/**
 * OnBoardingDataRetrieval
 *
 * This class retrieves relevant data required for basic
 * functionality of the app. It is called when the app is first
 * started.
 */
public class OnBoardingDataRetrieval {

    public interface RetrievalResult {
        void success();
        void error(int where, String whereStr, int errorCode);
        void timeout();
    }

    /**
     * Retrieve data from server.
     *
     * 1. Load users' saved recipes from server.
     * 2. Load users' recipes (recipes created by user).
     * 3. Load users' cooked recipes.
     * 4. Load recommended recipes from server.
     * 5. Call success callback.
     */
    public static void retrieve(String logTAG, RetrievalResult resultCallback) {
        String userSessId = LoggedInUser.user().getSessionID();

        // TODO: completely refactor, nested calls are big no-no!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // 1.) retrieve saved recipes from server, and save them locally
        Server.getAllSavedRecipes(userSessId, new ServerCallbacks.GetSavedRecipesResult() {
            @Override
            public void success(List<Recipe> savedRecipes) {
                Log.i(logTAG, "Saved recipes successfully retrieved from server!");
                LocalRecipes.i().addRecipes(savedRecipes, LocalRecipes.Type.SAVED);

                // 2.) get all recipes created by user
                Server.getAllOwnRecipes(userSessId, new ServerCallbacks.ListOfRecipesCallback() {
                    @Override
                    public void success(List<Recipe> createdRecipes) {
                        LocalRecipes.i().addRecipes(createdRecipes, LocalRecipes.Type.ADDED_BY_USER);

                        // 3.) get all recipes cooked by user
                        Server.getAllCookedRecipes(userSessId, new ServerCallbacks.ListOfRecipesCallback() {
                            @Override
                            public void success(List<Recipe> cookedRecipes) {
                                LocalRecipes.i().addRecipes(cookedRecipes, LocalRecipes.Type.COOKED_BY_USER);

                                // 4.) retrieve home feed from server
                                Server.getHomeFeed(userSessId, new ServerCallbacks.HomeFeedResultCallback() {
                                    @Override
                                    public void success(HomeFeedJson homeFeedJson) {
                                        Log.i(logTAG, "Successfully retrieved home feed from server!");
                                        LocalRecipes.i().addRecipes(homeFeedJson.getRecommendedRecipesInternal(), LocalRecipes.Type.RECOMMENDED);
                                        LocalRecipes.i().addRecipes(homeFeedJson.getPersonalizedRecipesInternal(), LocalRecipes.Type.PERSONALIZED);
                                        LoggedInUser.user().setHomeFeedCategories(homeFeedJson.getCategories());

                                        // 5.) call success result callback
                                        resultCallback.success();
                                    }
                                    @Override
                                    public void error(int errorCode) {
                                        Log.i(logTAG, "Error (" + errorCode + ") while retrieving recommended recipes from server!");
                                        resultCallback.error(2, "Getting recommended recipes from server", errorCode);
                                    }
                                    @Override
                                    public void timeout(Throwable exception) {
                                        Log.i(logTAG, "Takes too long to get home feed!");
                                        resultCallback.timeout();
                                    }
                                });
                            }
                            @Override
                            public void error(int errorCode) {
                            }
                        });
                    }
                    @Override
                    public void error(int errorCode) {
                    }
                });
            }

            // 1.) saved recipe retrieval error
            @Override
            public void error(int errorCode) {
                Log.i(logTAG, "Error while querying saved recipes from server (code: " + errorCode + ")!");
                resultCallback.error(1, "Getting saved recipes from server", errorCode);
            }

            // 1.) saved recipe retrieval timeout
            @Override
            public void timeout(Throwable t) {
                Log.i(logTAG, "Saved recipes retrieval timeout!");
                resultCallback.timeout();
            }
        });
    }
}
