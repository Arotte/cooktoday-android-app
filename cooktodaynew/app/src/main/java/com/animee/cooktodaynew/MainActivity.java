package com.animee.cooktodaynew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodaynew.recipe.RecipeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.bt_main_logout:
                intent.setClass(MainActivity.this,LoginActivity.class);
                break;
            case R.id.bt_main_recipt:
                intent.setClass(MainActivity.this, RecipeActivity.class);
                break;
            case R.id.bt_main_myaccount:
                intent.setClass(MainActivity.this, RecipeActivity.class);
                break;

        }
        startActivity(intent);
    }
}

