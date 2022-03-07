package com.abdn.cooktoday.onboarding.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abdn.cooktoday.R;

public class RegistrationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_result);

        // toggle check animation by:
        // mImgCheck = (ImageView) findViewById(R.id.imageView);
        // ((Animatable) mImgCheck.getDrawable()).start();
    }
}