package com.aron.cooktoday.onboarding.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aron.cooktoday.MainActivity;
import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.registration.RegisterActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // email login
        Button btn = (Button)findViewById(R.id.btnLoginEmail_activity_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // go to sign up activity
        LinearLayout tvSignUp = findViewById(R.id.btnGoToRegisterFromLogin);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });
    }
}