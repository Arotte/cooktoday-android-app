package com.aron.cooktoday.api;

import com.aron.cooktoday.api.jsonmodels.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
//    @POST("users")
//    Call<User> loginUser(@Body User comment);

    @FormUrlEncoded
    @POST("users")
    Call<User> loginUser(@Field("email") String email, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("users")
//    Call<Comment> deleteUser(@FieldMap Map<String, String> fields);
}