package com.aron.cooktoday.upload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aron.cooktoday.R;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        loadFragment(new UploadStepOneFragment());

        Button cancel = findViewById(R.id.btnCancelUpload);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.uploadStepsFragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}