package activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.animee.cooktodayapp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private Button btn_regist2;
    private Button btn_login2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // get control object
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        btn_regist2=findViewById(R.id.btn_regist2);
        btn_login2=findViewById(R.id.btn_login2);
        // set click event for register button
        btn_regist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}
