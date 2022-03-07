package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class LogoutMessageJSONModel {
    @SerializedName("msg")
    private String msg;

    public LogoutMessageJSONModel(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
