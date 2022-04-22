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
import com.abdn.cooktoday.utility.OnBoardingDataRetrieval;

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

            OnBoardingDataRetrieval.retrieve(TAG, new OnBoardingDataRetrieval.RetrievalResult() {
                @Override
                public void success() {
                    startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void error(int where, String whereStr, int errorCode) {
                    Log.i(TAG, "Error while retrieving data: " + errorCode + ", " + whereStr);
                }
            });
        }
    }


}
