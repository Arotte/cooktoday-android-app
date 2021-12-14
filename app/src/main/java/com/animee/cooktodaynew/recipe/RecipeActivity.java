package com.animee.cooktodaynew.recipe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.*;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodaynew.R;
import com.animee.cooktodaynew.bean.FoodBean;
import com.animee.cooktodaynew.bean.FoodUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener{
    EditText searchEt;
    ImageView searchIv, flushIv;
    ListView showLv;

    List<FoodBean>mDatas;
    List<FoodBean>allFoodList;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initView();
        mDatas = new ArrayList<>();
        allFoodList = FoodUtils.getAllFoodList();
        mDatas.addAll(allFoodList);

        adapter = new RecipeAdapter(this, mDatas);
        showLv.setAdapter(adapter); // set adapter
    }

    private void initView() {
        searchEt = findViewById(R.id.recipe_et_search);
        searchIv = findViewById(R.id.recipe_iv_search);
        flushIv = findViewById(R.id.recipe_iv_flush);
        showLv = findViewById(R.id.recipelist_lv);
        searchIv.setOnClickListener(this); // add click event listener
        flushIv.setOnClickListener(this); // add chick event listener

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recipe_iv_flush: // refresh click
                searchEt.setText("");
                mDatas.clear(); // clear old data reasource
                mDatas.addAll(allFoodList);
                adapter.notifyDataSetChanged();

                break;
            case R.id.recipe_iv_search: // search click
                String msg = searchEt.getText().toString().trim(); // get text
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this,"Input cannot be empty.",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<FoodBean>list = new ArrayList<>();
                for (int i = 0; i < allFoodList.size(); i++) {
                    String title = allFoodList.get(i).getTitle();
                    if (title.contains(msg)) {
                        list.add(allFoodList.get(i));
                    }
                }
                mDatas.clear(); // clear listview data resource
                mDatas.addAll(list); // add new data resource
                adapter.notifyDataSetChanged(); // notify adapter changed

                break;
        }

    }
}
