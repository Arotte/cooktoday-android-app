package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class ErrorJSON__Outer {

    @SerializedName("error")
    private final ErrorJSON error;

    public ErrorJSON__Outer(ErrorJSON error) {
        this.error = error;
    }

    public ErrorJSON getError() {
        return error;
    }
}
