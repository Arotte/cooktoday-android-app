package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class UserJSONModel__Outer {

    @SerializedName("user")
    private final UserJSONModel user;

    public UserJSONModel__Outer(UserJSONModel user) {
        this.user = user;
    }

    public UserJSONModel getUser() {
        return user;
    }
}
