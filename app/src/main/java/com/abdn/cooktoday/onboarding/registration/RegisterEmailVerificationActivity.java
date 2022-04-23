package com.abdn.cooktoday.onboarding.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.survey.SurveySlidePagerActivity;
import com.abdn.cooktoday.utility.Util;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RegisterEmailVerificationActivity extends AppCompatActivity {

    private static final int countdownMillis = 180000; // 3 minutes, 60k ms = 1m
    private static final int nCodeDigits = 4;
    private List<EditText> codeInputFields;
    private List<Integer> finalCode;

    Button btnVerify;
    Button btnNewCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email_verification);

        initCodeInput();
        focusField(0);
        showKeyboard();
        startCountdown();

        btnVerify = findViewById(R.id.btnVerifyCode);
        btnNewCode = findViewById(R.id.btnGetNewCode);

        // verify button press
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                startActivity(new Intent(RegisterEmailVerificationActivity.this, SurveySlidePagerActivity.class));
            }
        });

        // get new code button press
        btnNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                // snackbar notification
                LinearLayout layout = findViewById(R.id.verificationLayout);
                Snackbar snackbar = Snackbar.make(layout, "We've sent the new code to your email address", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    private void startCountdown() {
        TextView countdown = findViewById(R.id.tvCodeVerifCountdown);
        new CountDownTimer(countdownMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) Math.floor(millisUntilFinished / 60000);
                int seconds = (int) ((millisUntilFinished % 60000) / 1000);
                countdown.setText(prettyTimeStr(minutes, seconds));
            }
            public void onFinish() {
                TextView tv = findViewById(R.id.tvCodeExpires);
                tv.setText("Code");
                countdown.setText(" expired");
            }
        }.start();
    }

    private String prettyTimeStr(int m, int s) {
        StringBuilder str = new StringBuilder(" ");
        if (m < 10)
            str.append("0").append(m);
        else
            str.append(m);
        str.append(":");
        if (s < 10)
            str.append("0").append(s);
        else
            str.append(s);

        return str.toString();
    }

    private void initCodeInput() {
        codeInputFields = new ArrayList<>();
        finalCode = new ArrayList<>();
        codeInputFields.add(findViewById(R.id.etVerifCode1));
        codeInputFields.add(findViewById(R.id.etVerifCode2));
        codeInputFields.add(findViewById(R.id.etVerifCode3));
        codeInputFields.add(findViewById(R.id.etVerifCode4));

        for (int i = 0; i < nCodeDigits; i++) {
            finalCode.add(-1);
            int finalI = i;
            codeInputFields.get(i).addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        codeInputFields.get(finalI).clearFocus();
                        if (finalI != nCodeDigits - 1) {
                            codeInputFields.get(finalI + 1).requestFocus();
                        } else {
                            codeInputFields.get(finalI).clearFocus();
                            Util.hideKeyboardIfVisible(RegisterEmailVerificationActivity.this, codeInputFields.get(finalI));
                            System.out.println("CODE INPUT FINISHED");
                        }
                        finalCode.set(finalI, Integer.parseInt(codeInputFields.get(finalI).getText().toString()));
                        printCode();
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        }
    }

    private void focusField(int i) {
        if (i > nCodeDigits || i < 0)
            return;
        codeInputFields.get(i).requestFocus();
    }

    private void showKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void printCode() {
        System.out.print("CODE PROVIDED BY USER: ");
        StringBuilder codeStr = new StringBuilder("");
        for (int i = 0; i < nCodeDigits; i++) {
            if (finalCode.get(i) != -1)
                codeStr.append(finalCode.get(i) + " ");
            else
                codeStr.append("_ ");
        }
        System.out.println(codeStr);
    }
}