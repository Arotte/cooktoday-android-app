package com.animee.cooktodaynew.recipe;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodaynew.R;
import com.animee.cooktodaynew.bean.FoodBean;
import com.animee.cooktodaynew.bean.FoodUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    EditText searchEt;
    ImageView searchIv, flushIv;
    ListView recipelistLv;
    // listView inner database
    List<FoodBean>mDatas;
    List<FoodBean>allFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initView();
        // 2. find the database resource
        mDatas = new ArrayList<>();
        allFoodList = FoodUtils.getAllFoodList();
        mDatas.addAll(allFoodList);
        // 3. create adapter, BaseAdapter child method
    }

    private void initView() {
        searchEt = findViewById(R.id.recipe_et_search);
        searchIv = findViewById(R.id.recipe_iv_search);
        flushIv = findViewById(R.id.recipe_iv_flush);
        recipelistLv = findViewById(R.id.recipe_lv_recipelist);
    }
}
