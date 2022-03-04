package com.aron.cooktoday.onboarding.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aron.cooktoday.MainActivity;
import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.registration.RegisterActivity;
import com.aron.cooktoday.ui.login.LoginActivity;


public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        // reset password
        Button btn = (Button)findViewById(R.id.btn_forgot_password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }
}