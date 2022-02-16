package com.aron.cooktoday.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.aron.cooktoday.R;
import com.aron.cooktoday.onboarding.registration.RegisterActivity;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);

        // go to sign in activity
        Button btn = (Button) findViewById(R.id.btn_onboarding_letsgo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO check if this is first run of the app
                startActivity(new Intent(OnBoardingActivity.this, RegisterActivity.class));
            }
        });
    }
}
