package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class ErrorJSON {

    @SerializedName("message")
    private String message;
    @SerializedName("name")
    private String name;
    @SerializedName("statusCode")
    private String statusCode;

    public ErrorJSON(String message, String name, String statusCode) {
        this.message = message;
        this.name = name;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
