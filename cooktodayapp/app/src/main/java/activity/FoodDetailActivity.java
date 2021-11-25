package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodayapp.R;

import java.io.IOException;

import entity.FoodEntity;
import utils.RemoteDatas;

public class FoodDetailActivity extends AppCompatActivity {

    FoodEntity mFoodEntity;
    ImageView fooddetail_foodPicIv,fooddetail_scIv;
    TextView fooddetail_nameTv,fooddetail_yclTv,fooddetail_viewnumTv;
    WebView fooddetail_zfWv;
    FoodsDetailHandler mFoodsDetailHandler;
    FoodsDetailHandler1 mFoodsDetailHandler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        initView();
        initData();
    }

    private void initData() {
        mFoodEntity = (FoodEntity) getIntent().getExtras().get("foodEntity");

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("id", mFoodEntity.getId()+"")
                .build();
        Request request = new Request.Builder()
                .url(ItFxqConstants.GETFOODBYID_URL)
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Message msg = new Message();
                msg.what = ItFxqConstants.OK_STATUS;
                msg.obj = res;
                mFoodsDetailHandler.sendMessage(msg);
            }
            @Override
            public void onFailure(Call call, IOException e) {

            }
        });




    }

    class FoodsDetailHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case ItFxqConstants.OK_STATUS:
                    if (msg.obj != null) {
                        String resultJson = (String) msg.obj;
                        //解析获取的JSON数据
                        mFoodEntity = RemoteDatas.INSTANCE.getFoodEntity(resultJson);
                        Glide.with(getApplicationContext())
                                .load(mFoodEntity.getFoodPic())
                                .into(fooddetail_foodPicIv);

                        fooddetail_nameTv.setText("dish name:"+mFoodEntity.getFoodName());
                        fooddetail_yclTv.setText("material:"+mFoodEntity.getYcl());
                        fooddetail_viewnumTv.setText(mFoodEntity.getViewnum()+"");

                        String htmlPart1 = "<!DOCTYPE HTML html>\n" +
                                "<head><meta charset=\"utf-8\"/>\n" +
                                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                "<style> \n" +
                                "img{width:100%!important;height:auto!important}\n" +
                                " </style>";
                        String htmlPart2 = "</body></html>";

                        fooddetail_zfWv.getSettings().setJavaScriptEnabled(true);

                        String html = htmlPart1 + mFoodEntity.getZf() + htmlPart2;

                        fooddetail_zfWv.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                        fooddetail_zfWv.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView view, String url) {
                            }
                        });

                    }
                    break;

            }
        }
    }
    class FoodsDetailHandler1 extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case ItFxqConstants.OK_STATUS:
                    Toast.makeText(FoodDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void initView() {
        fooddetail_foodPicIv = findViewById(R.id.fooddetail_foodPicIv);
        fooddetail_nameTv = findViewById(R.id.fooddetail_nameTv);
        fooddetail_yclTv = findViewById(R.id.fooddetail_yclTv);
        fooddetail_zfWv = findViewById(R.id.fooddetail_zfWv);
        fooddetail_viewnumTv = findViewById(R.id.fooddetail_viewnumTv);
        fooddetail_scIv = findViewById(R.id.fooddetail_scIv);
        mFoodsDetailHandler = new FoodsDetailHandler();
        mFoodsDetailHandler1 = new FoodsDetailHandler1();
        fooddetail_scIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("foodid", mFoodEntity.getId()+"")
                        .add("username", CommonUtils.getLoginUser(getApplicationContext()).getUsername())
                        .build();
                Request request = new Request.Builder()
                        .url(ItFxqConstants.SCFOOD_URL)
                        .post(requestBody).build();
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Message msg = new Message();
                        msg.what = ItFxqConstants.OK_STATUS;

                        mFoodsDetailHandler1.sendMessage(msg);
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });


            }
        });
    }

    public void backPage(View view){
        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }
}