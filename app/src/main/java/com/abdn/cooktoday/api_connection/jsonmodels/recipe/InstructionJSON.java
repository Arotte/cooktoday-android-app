package com.abdn.cooktoday.api_connection.jsonmodels.recipe;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstructionJSON {
    @SerializedName("_id")
    private String id;
    private String text;
    // private ArrayList<String> ingredients;


    public InstructionJSON(String id, String text) {
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
