package com.abdn.cooktoday.onboarding.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abdn.cooktoday.R;
import com.abdn.cooktoday.api_connection.APIRepository;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel__Outer;
import com.abdn.cooktoday.local_data.Cache;
import com.abdn.cooktoday.local_data.LoggedInUser;
import com.abdn.cooktoday.local_data.model.User;
import com.abdn.cooktoday.onboarding.login.LoginActivity;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RegisterActivity
 *
 * Activity for registering a new user.
 * It performs passwords validation and sends the user data to the server.
 *
 * @author Team Alpha, University of Aberdeen
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    EditText pwField;
    EditText emailField;
    EditText nickNameField;

    Button register;
    ProgressBar progressBar;
    ImageView loginIcon;
    ImageView successIcon;

    // pw requirements
    private boolean MINCHAR = false;
    private boolean CONTAINS_NUM = false;
    private boolean CONTAINS_UPPERCASE = false;
    private final int NUM_REQ = 2;
    private final int MINCHAR_REQ = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //

        emailField = findViewById(R.id.etSignupEmailField);
        pwField = findViewById(R.id.etSignupPwField);
        nickNameField = findViewById(R.id.etSignupNicknameField);
        register = findViewById(R.id.btnCreateAccount_activity_signup);
        LinearLayout goToLogin = findViewById(R.id.goToLoginFromSignup);
        togglePwChecksActive();

        // validate pw input
        pwField.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                MINCHAR = s.length() >= MINCHAR_REQ;
                CONTAINS_NUM = containsNum(s.toString());
                CONTAINS_UPPERCASE = containsUppercase(s.toString());
                togglePwChecksActive();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // go to login screen
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        initStatusIndicatorIcons();
        watchIfRegisterInitiatedByUser();
    }

    private void registerInitiatedByUser() {
        Log.i(TAG, "Registering...");
        loginIcon.setVisibility(View.INVISIBLE);
        successIcon.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        // get & validate email & pw
        if (!pwValid()) {
            showLoginIconOnMainButton();
            return;
        }

        String email = emailField.getText().toString();
        String pw = pwField.getText().toString();
        String nickname = nickNameField.getText().toString();

        // make register request to server (on background thread):
        Executor regExec = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };
        regExec.execute(new Runnable() {
            @Override
            public void run() {
                APIRepository.getInstance().getUserService().registerUser(email, pw, nickname, "notGiven").enqueue(new Callback<UserJSONModel__Outer>() {
                    @Override
                    public void onResponse(Call<UserJSONModel__Outer> call, Response<UserJSONModel__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, String.valueOf(r.body().getUser()));
                            User user = new User(
                                    r.body().getUser().getEmail(),
                                    r.body().getUser().getId(),
                                    r.body().getUser().getFirstName(),
                                    r.body().getUser().getLastName(),
                                    r.body().getUser().getRole(),
                                    r.headers().get("Set-Cookie") // get session ID from response headers
                            );
                            onRegisterSuccess(user, pw);
                        } else if (r.code() == 400) {
                            try {
                                String strErrorMsg = r.errorBody().string();
                                if (strErrorMsg.contains("User with such email already exists") && strErrorMsg.contains("UserInputError")) {
                                    // user with email already exists
                                    showLoginIconOnMainButton();
                                    showExistingEmailToast();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            onRegisterFailure(r.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserJSONModel__Outer> call, Throwable t) {
                        Log.i(TAG, t + ", " + t.getMessage());
                        onRegisterFailure(-1);
                    }
                });
            }
        });
    }

    private void onRegisterFailure(int code) {
        // TODO: better error handling!
        Log.i(TAG, "User registration FAILED! Code: " + code);
        showLoginIconOnMainButton();
    }

    private void showLoginIconOnMainButton() {
        loginIcon.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        successIcon.setVisibility(View.INVISIBLE);
    }

    private void onRegisterSuccess(User user, String pw) {
        // log in registered user
        Log.i("LoginActivity", "User registration SUCCESSFUL, now logging user in...");

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
                APIRepository.getInstance().getUserService().loginUser(user.getEmail(), pw).enqueue(new Callback<UserJSONModel__Outer>() {
                    @Override
                    public void onResponse(Call<UserJSONModel__Outer> call, Response<UserJSONModel__Outer> r) {
                        if (r.code() == 200) {
                            Log.i(TAG, String.valueOf(r.body().getUser()));
                            User user = new User(
                                    r.body().getUser().getEmail(),
                                    r.body().getUser().getId(),
                                    r.body().getUser().getFirstName(),
                                    r.body().getUser().getLastName(),
                                    r.body().getUser().getRole(),
                                    r.headers().get("Set-Cookie") // get session ID from response headers
                            );
                            Cache.write_bool(Cache.KEY_USER_LOGGED_IN, true);
                            Cache.write_string(Cache.KEY_USER_EMAIL, user.getEmail());
                            Cache.write_string(Cache.KEY_USER_ID, user.getServerId());
                            Cache.write_string(Cache.KEY_USER_FNAME, user.getFirstName());
                            Cache.write_string(Cache.KEY_USER_LNAME, user.getLastName());
                            Cache.write_string(Cache.KEY_USER_ROLE, user.getRole());
                            Cache.write_string(Cache.KEY_USER_SESSID, user.getSessionId());

                            Log.i("LoginActivity", "User registration and login SUCCESSFUL!");
                            loginIcon.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            successIcon.setVisibility(View.VISIBLE);

                            LoggedInUser.user().setUser(user);
                            finish();
                            startActivity(new Intent(RegisterActivity.this, RegistrationResultActivity.class));
                        } else {
                            Log.i(TAG, "Registration successful, but login FAILED!");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserJSONModel__Outer> call, Throwable t) {
                        Log.i(TAG, "Registration successful, but login FAILED!");
                    }
                });
            }
        });
    }

    private void showExistingEmailToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_cooktoday_error, findViewById(R.id.toastCookTodayError));

        ImageView image = layout.findViewById(R.id.ivToastCookTodayError);
        image.setImageResource(R.drawable.ic_info_circle);
        image.setColorFilter(getResources().getColor(R.color.white));
        TextView text = layout.findViewById(R.id.tvToastCookTodayError);
        text.setText("Oops! Email already exists!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 35);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void watchIfRegisterInitiatedByUser() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerInitiatedByUser();
            }
        });
        pwField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    registerInitiatedByUser();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean containsUppercase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean containsNum(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                n++;
        }
        return n >= NUM_REQ;
    }

    private void initStatusIndicatorIcons() {
        progressBar = findViewById(R.id.pbRegister);
        loginIcon = findViewById(R.id.icRegisterRegisterIcon);
        successIcon = findViewById(R.id.icRegisterSuccessIcon);

        showLoginIconOnMainButton();
    }

    private boolean pwValid() {
        return MINCHAR && CONTAINS_NUM && CONTAINS_UPPERCASE;
    }

    private void togglePwChecksActive() {
        ImageView ivMinChar = findViewById(R.id.ivPwReqMinChars);
        TextView tvMinChar = findViewById(R.id.tvPwReqMinChars);
        ImageView ivContainsNum = findViewById(R.id.ivPwReqContainsNum);
        TextView tvContainsNum = findViewById(R.id.tvPwReqContainsNum);
        ImageView ivContainsUppercase = findViewById(R.id.ivPwReqUppercase);
        TextView tvContainsUppercase = findViewById(R.id.tvPwReqUppercase);

        if (MINCHAR) {
            ivMinChar.setBackgroundResource(R.drawable.ic_check_active);
            tvMinChar.setTextColor(getResources().getColor(R.color.textMain));
        } else {
            ivMinChar.setBackgroundResource(R.drawable.ic_check_inactive);
            tvMinChar.setTextColor(getResources().getColor(R.color.textSecondary));
        }

        if (CONTAINS_NUM) {
            ivContainsNum.setBackgroundResource(R.drawable.ic_check_active);
            tvContainsNum.setTextColor(getResources().getColor(R.color.textMain));
        } else {
            ivContainsNum.setBackgroundResource(R.drawable.ic_check_inactive);
            tvContainsNum.setTextColor(getResources().getColor(R.color.textSecondary));
        }

        if (CONTAINS_UPPERCASE) {
            ivContainsUppercase.setBackgroundResource(R.drawable.ic_check_active);
            tvContainsUppercase.setTextColor(getResources().getColor(R.color.textMain));
        } else {
            ivContainsUppercase.setBackgroundResource(R.drawable.ic_check_inactive);
            tvContainsUppercase.setTextColor(getResources().getColor(R.color.textSecondary));
        }
    }
}