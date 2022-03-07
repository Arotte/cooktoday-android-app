package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.api_connection.services.UserService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creates and returns Services for
 * the different endpoints and
 * functionalities of the system.
 */
public final class APIRepository {
    private static APIRepository instance;

    private final UserService userService;

    public static APIRepository getInstance() {
        if (instance == null) {
            instance = new APIRepository();
        }
        return instance;
    }

    public APIRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIEndpoints.BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }

    public UserService getUserService() {
        return userService;
    }
}