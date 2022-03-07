package com.abdn.cooktoday.onboarding.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdn.cooktoday.MainActivity;
import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.APIRepository;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel__Outer;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.model.User;
import com.abdn.cooktoday.onboarding.forgotpassword.ForgotPassword;
import com.abdn.cooktoday.onboarding.registration.RegisterActivity;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText emailField;
    EditText pwField;
    Button loginBtn;
    ProgressBar progressBar;
    ImageView loginIcon;
    ImageView successIcon;
    TextView forgotPw;
    LinearLayout goToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initViewElements();
        watchIfLoginInitiatedByUser();
    }

    private void initViewElements() {
        emailField = findViewById(R.id.etLoginUsername);
        pwField = findViewById(R.id.etLoginPassword);
        loginBtn = findViewById(R.id.btnLoginLogin);
        forgotPw = findViewById(R.id.forgotPw);
        progressBar = findViewById(R.id.pbLogin);
        loginIcon = findViewById(R.id.icLoginLoginIcon);
        successIcon = findViewById(R.id.icLoginSuccessIcon);
        goToRegister = findViewById(R.id.btnGoToRegisterFromLogin);

        loginIcon.setVisibility(View.VISIBLE);
        successIcon.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                finish();
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void watchIfLoginInitiatedByUser() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInitiatedByUser();
            }
        });
        pwField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    loginInitiatedByUser();
                    return true;
                }
                return false;
            }
        });
    }

    private void loginInitiatedByUser() {
        Log.i("LoginActivity", "Logging user in...");
        loginIcon.setVisibility(View.INVISIBLE);
        successIcon.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        // get & validate email & pw
        String email = emailField.getText().toString();
        String pw = pwField.getText().toString();

        // make login request to server (on background thread):
        Executor loginExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };
        loginExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getUserService().loginUser(email, pw).enqueue(new Callback<UserJSONModel__Outer>() {
                    @Override
                    public void onResponse(Call<UserJSONModel__Outer> call, Response<UserJSONModel__Outer> r) {
                        if (r.code() == 200) {
                            Log.i("LoginActivity", String.valueOf(r.body().getUser()));
                            User user = new User(
                                   r.body().getUser().getEmail(),
                                   r.body().getUser().getId(),
                                   r.body().getUser().getFirstName(),
                                   r.body().getUser().getLastName(),
                                   r.body().getUser().getRole(),
                                   r.headers().get("Set-Cookie") // get session ID from response headers
                            );
                            onLoginSuccess(user);
                        } else {
                            onLoginFailure(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserJSONModel__Outer> call, Throwable t) {
                        onLoginFailure(-1);
                    }
                });
            }
        });
    }


    private void onLoginSuccess(User user) {
        Log.i("LoginActivity", "User login SUCCESSFUL!");
        loginIcon.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        successIcon.setVisibility(View.VISIBLE);

        saveLoggedInUserToCache(user);

        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void onLoginFailure(int httpErrorCode) {
        Log.i("LoginActivity", "User login FAILED!");
        loginIcon.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        successIcon.setVisibility(View.INVISIBLE);
    }

    private void saveLoggedInUserToCache(User user) {
        Cache.write_bool(Cache.KEY_USER_LOGGED_IN, true);
        Cache.write_string(Cache.KEY_USER_EMAIL, user.getEmail());
        Cache.write_string(Cache.KEY_USER_ID, user.getServerId());
        Cache.write_string(Cache.KEY_USER_FNAME, user.getFirstName());
        Cache.write_string(Cache.KEY_USER_LNAME, user.getLastName());
        Cache.write_string(Cache.KEY_USER_ROLE, user.getRole());
        Cache.write_string(Cache.KEY_USER_SESSID, user.getSessionId());
    }
}