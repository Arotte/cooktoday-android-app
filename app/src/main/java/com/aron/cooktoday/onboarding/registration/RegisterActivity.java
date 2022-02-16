package com.aron.cooktoday.onboarding.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.login.LogInActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText pwField;

    // pw requirements
    private boolean MINCHAR = false;
    private final int MINCHAR_REQ =  6;
    private boolean CONTAINS_NUM = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        pwField = findViewById(R.id.etSignupPwField);
        Button btnSignup = findViewById(R.id.btnCreateAccount_activity_signup);
        LinearLayout goToLogin = findViewById(R.id.goToLoginFromSignup);
        toggleActive();

        // validate pw input
        pwField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                MINCHAR = s.length() >= MINCHAR_REQ;
                CONTAINS_NUM = containsNum(s.toString());
                toggleActive();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // signup btn press
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, RegisterEmailVerificationActivity.class));
            }
        });

        // go to login screen
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
            }
        });
    }

    private boolean containsNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                return true;
        }
        return false;
    }

    private void toggleActive() {
        ImageView ivMinChar = findViewById(R.id.ivPwReqMinChars);
        TextView tvMinChar = findViewById(R.id.tvPwReqMinChars);
        ImageView ivContainsNum = findViewById(R.id.ivPwReqContainsNum);
        TextView tvContainsNum = findViewById(R.id.tvPwReqContainsNum);

        if (MINCHAR) {
            ivMinChar.setBackgroundResource(R.drawable.ic_check_active);
            tvMinChar.setTextColor(getResources().getColor(R.color.textMain));
        } else {
            ivMinChar.setBackgroundResource(R.drawable.ic_check_inactive);
            tvMinChar.setTextColor(getResources().getColor(R.color.textSecondary));
        }

        if (CONTAINS_NUM) {
            ivContainsNum.setBackgroundResource(R.drawable.ic_check_active);
            tvContainsNum.setTextColor(getResources().getColor(R.color.textMain));
        } else {
            ivContainsNum.setBackgroundResource(R.drawable.ic_check_inactive);
            tvContainsNum.setTextColor(getResources().getColor(R.color.textSecondary));
        }
    }
}