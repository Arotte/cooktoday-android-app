package com.abdn.cooktoday.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.onboarding.login.LoginActivity;
import com.abdn.cooktoday.utility.OnBoardingDataRetrieval;

/**
 * OnBoardingActivity
 *
 * This activity is the first activity that is shown when the app is opened.
 * It is responsible for displaying the on boarding screen and allowing the user to
 * to proceed to the login screen.
 *
 * It checks if the user has already logged in before and if so, it will skip the
 * login/signup funnel, and proceed to the main activity.
 *
 * It is also responsible for retrieving essential data from the server.
 *
 */
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
            Button btn = findViewById(R.id.btn_onboarding_letsgo);
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

            // retrieve essential data from the server
            OnBoardingDataRetrieval.retrieve(TAG, new OnBoardingDataRetrieval.RetrievalResult() {
                @Override
                public void success() {
                    // we got all the data we need, go to main activity
                    startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void error(int where, String whereStr, int errorCode) {
                    // something went wrong
                    Log.i(TAG, "Error while retrieving data: " + errorCode + ", " + whereStr);
                }
            });
        }
    }


}
