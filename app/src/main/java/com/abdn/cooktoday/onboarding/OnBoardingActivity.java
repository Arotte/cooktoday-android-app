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
import com.abdn.cooktoday.onboarding.login.LoginActivity;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cache.init(this);
        checkIfAlreadyLoggedIn();

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
    }

    private void checkIfAlreadyLoggedIn() {
        if (!Cache.read_bool(Cache.KEY_USER_LOGGED_IN, false)) {
            // not logged in
            Log.i("LoginActivity", "User not logged in. Starting login flow.");
        } else {
            // logged in
            Log.i("LoginActivity", "User already logged in. Skipping login flow.");
            startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
            finish();
        }
    }
}
