package activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_register;
    private Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViews();
        // set click event for login button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user input
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                String url = "?username=" + userName + "&password=" + passWord;
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                MyAsyncTask.execute(ConfigUtil.URL + "LoginServlet" + url);
            }
        });
        // set click event for register button, click register jump to activity register screen
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Intent.setClass(LoginActivity.this, RegisterActivity.class);//zhanshi
                startActivity(intent);
            }
        });
    }

    // get control object
    private void getViews() {
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_skip = findViewById(R.id.btn_skip);
    }

    // set 异步任务类 for login button
    class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String s = objects[0].toString();
            try {
                URL url = new URL(s);
                Log.e("url:", s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                InputStream in = urlConnection.getErrorStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(in)));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                if (response.toString().equals("1")) {
                    Log.e("1", "login sussessful");
                    publishProgress("1");
                } else {
                    if (response.toString().equals("-1")) {
                        Log.e("2", "Login failed");
                        publishProgress("-1");
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Object[] values){
            String flag=values[0].toString();
            if(flag.equals("1")){ // means login sussessful
                Toast.makeText(LoginActivity.this,"login sussessful",Toast.LENGTH_SHORT).show();
                // jump to main screen
                Intent intent= new Intent();
                intent.putExtra("usertel",username.getText().toString());
                intent.setClass(LoginActivity.this, MainFragmentActivity.class);
                startActivity(intent);
            }
            else if(flag.equals("-1")){
                Toast.makeText(LoginActivity.this,"username or passsword wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
