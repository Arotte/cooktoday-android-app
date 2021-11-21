package activity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Call;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodayapp.R;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adapter.FoodAdapter;
import entity.FoodEntity;
import utils.ItFxqConstants;
import utils.RemoteDatas;


public class FoodsActivity extends AppCompatActivity implements View.OnClickListener {
    private FoodEntity mFoodEntity;
    private TextView foodNameTv;
    private ImageView foodPicIv;
    private FoodAdapter mFoodAdapter;
    public static final int OKSTATUS = 1;
    private FoodsHandler mFoodsHandler;
    private ListView foodsListView;
    private List<FoodEntity> foodList = new ArrayList<>(); // all food
    private ListView listLv;
    SearchView menuSV;
    private String foodType;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        
        mFoodsHandler = new FoodsHandler();
        mFoodAdapter = new FoodAdapter(this);
        foodsListView = findViewById(R.id.listLv);
        menuSV = findViewById(R.id.menuSV);
        menuSV.setSubmitButtonEnabled(true);
        foodType = getIntent().getStringExtra("foodType");
        init();
        
    }
    private void init(){
        // initial adapter
        initAdapter();
        // initial data
        initData();
        // initial view
        initView();
        setData();
        initEvent();
    }

    private void initEvent() {
        menuSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // active when click search button
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                List tempList = new ArrayList();
                tempList = foodList.stream().filter(foodEntity -> foodEntity.getFoodName()
                        .contains(keyword))
                        .collect(Collectors.toList());
                mFoodAdapter.setData(tempList);
                mFoodAdapter.notifyDataSetChanged();
                return false;
            }
            // active when user input character
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void initAdapter() {
        mFoodAdapter = new FoodAdapter(this);
        foodsListView.setAdapter(mFoodAdapter);
    }

    private void initView() {
        foodNameTv = findViewById(R.id.foodNameTv);
        foodPicIv = findViewById(R.id.foodPicIv);
        listLv = (ListView) findViewById(R.id.listLv);
    }
    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        DownloadManager.Request request = new Request.Builder().url(ItFxqConstants.FOOD_URl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Call.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = OKSTATUS;
                msg.obj = res;
                mFoodsHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call, IOException e){
            }
        });
    }
    private void setData() {
        if (mFoodEntity == null) return;
        foodNameTv.setText(mFoodEntity.getFoodName());
        Glide.with(this)
                .load(mFoodEntity.getFoodPic())
                .error(R.mipmap.ic_launcher)
                .into(foodPicIv);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }


    private class FoodsHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg){
            super.dispatchMessage(msg);
            switch (msg.what){
                case OKSTATUS:
                    if(msg.obj != null){
                        String resultJson = (String) msg.obj;
                        foodList = RemoteDatas.INSTANCE.getFoodList(resultJson);
                        if("1".equals(foodType)){
                            foodList = foodList.stream().filter(foodEntity ->
                                    foodEntity.getFoodType().equals"1")
                            ).collect(Collectors.toList());
                        }else if("2".equals(foodType)){
                                foodList = foodList.stream().filter(foodEntity ->
                                        foodEntity.getFoodType().equals("2")
                                ).collect(Collectors.toList());
                            }else if("3".equals(foodType)){
                                foodList = foodList.stream().filter(foodEntity ->
                                        foodEntity.getFoodType().equals("3")
                                ).collect(Collectors.toList());
                            }else if("4".equals(foodType)){
                                foodList = foodList.stream().filter(foodEntity ->
                                        foodEntity.getFoodType().equals("5") ||  foodEntity.getFoodType().equals("6")
                                ).collect(Collectors.toList());
                        }
                        mFoodAdapter.setData(foodList);
                        mFoodAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }
}

























