package com.abdn.cooktoday.api_connection.services;

import com.abdn.cooktoday.api_connection.jsonmodels.LogoutMessageJSONModel;
import com.abdn.cooktoday.api_connection.jsonmodels.UserJSONModel__Outer;
import com.abdn.cooktoday.api_connection.jsonmodels.UserPrefsJsonModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
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
    Call<UserJSONModel__Outer> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    /**
     *
     * @return
     */
    @POST("auth/logout/")
    Call<LogoutMessageJSONModel> logoutUser();

    /**
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    @FormUrlEncoded
    @POST("user/")
    Call<UserJSONModel__Outer> registerUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName
    );

    /**
     *
     * @param userSessId
     * @param userPrefs
     * @return
     */
    @POST("user/prefs/")
    Call<UserPrefsJsonModel> saveUserPrefs(
            @Header("Cookie") String userSessId,
            @Body UserPrefsJsonModel userPrefs
    );

//    @FormUrlEncoded
//    @POST("users")
//    Call<Comment> deleteUser(@FieldMap Map<String, String> fields);
}