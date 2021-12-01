package com.animee.cooktodaynew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword;
    private RelativeLayout mRlRegisteractivityBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword = findViewById(R.id.et_registeractivity_password);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        mIvRegisteractivityBack.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.iv_registeractivity_back: // back to login
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_registeractivity_register: // register button
                // get username and password
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(username)&& !TextUtils.isEmpty(password)){
                    mDBOpenHelper.add(username, password);
                    Intent intent2 = new Intent(this,MainActivity.class);
                    startActivity(intent2);
                    finish();
                    Toast.makeText(this, "Register successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Not enough imformation, register failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
