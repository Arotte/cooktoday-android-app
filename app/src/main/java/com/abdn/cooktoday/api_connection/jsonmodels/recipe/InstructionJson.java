package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstructionJson {
    @SerializedName("_id")
    private String id;
    private String text;
    // private ArrayList<String> ingredients;


    public InstructionJson(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
