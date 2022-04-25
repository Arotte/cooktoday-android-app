package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class LogoutMessageJSONModel {
    @SerializedName("msg")
    private final String msg;

    public LogoutMessageJSONModel(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
