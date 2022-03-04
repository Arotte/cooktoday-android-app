package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    /**
     * User login using password auth.
     * @param email Email of the user.
     * @param password Password of the user.
     * @return The user in UserJSONModel format.
     */
    @FormUrlEncoded
    @POST("login/password/")
    Call<UserJSONModel> loginUser(@Field("email") String email, @Field("password") String password);

//    @FormUrlEncoded
//    @POST("users")
//    Call<Comment> deleteUser(@FieldMap Map<String, String> fields);
}