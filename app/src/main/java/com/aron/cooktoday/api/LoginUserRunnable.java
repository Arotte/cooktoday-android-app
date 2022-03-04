package com.aron.cooktoday.api;

import android.widget.Toast;

import com.aron.cooktoday.api.jsonmodels.User;

import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserRunnable implements Runnable {

    private static final String BASE_URL = "https://cooktoday-api.herokuapp.com/";
    private static final String AUTH_ENDPOINT = "api/auth/";
    private static final String USER_ENDPOINT = "api/user/";

    private String email;
    private String pw;

    public LoginUserRunnable(String email, String pw) {
        this.email = email;
        this.pw = pw;
    }

    @Override
    public void run() {
        UserRepository.getInstance().getUserService().loginUser(email, pw).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> r) {
                System.out.println("USER LOGGED IN: " + r.body());
                // Toast.makeText(getApplicationContext(), "Comment " + r.body().getComment() + " created", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("ERROR LOGGING IN USER: " + t.getMessage());
                // Toast.makeText(getApplicationContext(), "Error Creating Comment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
