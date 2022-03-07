package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.LogoutMessageJSONModel;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel__Outer;

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
    @POST("auth/login/password/")
    Call<UserJSONModel__Outer> loginUser(@Field("email") String email, @Field("password") String password);

    @POST("auth/logout/")
    Call<LogoutMessageJSONModel> logoutUser();

    @FormUrlEncoded
    @POST("user/register/")
    Call<UserJSONModel__Outer> registerUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName);

//    @FormUrlEncoded
//    @POST("users")
//    Call<Comment> deleteUser(@FieldMap Map<String, String> fields);
}