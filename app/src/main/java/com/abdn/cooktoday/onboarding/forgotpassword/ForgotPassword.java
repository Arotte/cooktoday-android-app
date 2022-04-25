package com.abdn.cooktoday.onboarding.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.login.LoginActivity;


public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        // reset password
        Button btn = findViewById(R.id.btn_forgot_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
}