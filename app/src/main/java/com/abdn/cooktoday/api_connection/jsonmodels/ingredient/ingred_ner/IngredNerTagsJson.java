package com.abdn.cooktoday.api_connection.jsonmodels.ingredient.ingred_ner;

import com.google.gson.annotations.SerializedName;

public class IngredNerTagsJson {
    @SerializedName("QUANTITY")
    private String quantity;
    @SerializedName("UNIT")
    private String unit;
    @SerializedName("NAME")
    private String name;
    @SerializedName("O")
    private String nil;

    public IngredNerTagsJson(String quantity, String unit, String name, String nil) {
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
        this.nil = nil;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public String getNil() {
        return nil;
    }
}
