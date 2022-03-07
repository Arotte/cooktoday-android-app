package com.abdn.cooktoday.onboarding.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.onboarding.survey.SurveySlidePagerActivity;

public class RegistrationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_result);

        checkAnimation();
        actionButton();
    }

    private void actionButton() {
        ((Button) findViewById(R.id.btnRegResultLetsGo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationResultActivity.this, SurveySlidePagerActivity.class));
                finish();
            }
        });
    }

    private void checkAnimation() {
        // toggle check animation after 1 second
        CountDownTimer timer = new CountDownTimer(400, 400) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                ImageView imgCheck = (ImageView) findViewById(R.id.ivRegResultOKCheckAnim);
                ((Animatable) imgCheck.getDrawable()).start();
            }
        };
        timer.start();
    }
}