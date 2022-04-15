package com.abdn.cooktoday.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.Server;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.Recipe;
import com.abdn.cooktoday.onboarding.login.LoginActivity;

import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private static final String TAG = "OnBoardingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cache.init(this);
        checkIfAlreadyLoggedIn();
    }

    private void checkIfAlreadyLoggedIn() {
        if (!Cache.read_bool(Cache.KEY_USER_LOGGED_IN, false)) {
            // not logged in
            Log.i("LoginActivity", "User not logged in. Starting login flow.");
            setContentView(R.layout.activity_getstarted);
            // go to sign in activity
            Button btn = (Button) findViewById(R.id.btn_onboarding_letsgo);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                    finish();
                }
            });
        } else {
            // logged in
            setContentView(R.layout.activity_getstarted_splash);
            Log.i("LoginActivity", "User already logged in. Skipping login flow.");
            LoggedInUser.user().setUser(Cache.read_logged_in_user());
            retrieveDataFromServer();
        }
    }

    /**
     * Retrieve data from server, then start MainActivity.
     *
     * 1. Load user's saved recipes from server.
     * 2. Load recommended recipes from server.
     * 3. Start MainActivity.
     */
    private void retrieveDataFromServer() {
        // 1.) retrieve saved recipes from server, and save them locally
        Server.getAllSavedRecipes(LoggedInUser.user().getSessionID(), new Server.GetSavedRecipesResult() {
            @Override
            public void success(List<Recipe> recipes) {
                Log.i(TAG, "Saved recipes successfully retrieved from server!");
                LoggedInUser.user().setSavedRecipes(recipes);

                // 2.) retrieve recommended recipes from server, and save them
                Server.getRecommendedRecipes(LoggedInUser.user().getSessionID(), new Server.GetRecommendedRecipesResult() {
                    @Override
                    public void success(List<Recipe> recommendedRecipes) {
                        Log.i(TAG, "Successfully retrieved recommended recipes from server!");
                        LoggedInUser.user().setRecommendedRecipes(recommendedRecipes);

                        // 3.) start MainActivity
                        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                        finish();
                    }
                    @Override
                    public void error(int errorCode) {
                        Log.i(TAG, "Error (" + errorCode + ") while retrieving recommended recipes from server!");
                    }
                });
            }
            @Override
            public void error(int errorCode) {
                Log.i(TAG, "Error while querying saved recipes from server (code: " + errorCode + ")!");
            }
        });
    }
}
