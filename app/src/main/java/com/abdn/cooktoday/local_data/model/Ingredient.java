package com.abdn.cooktoday.local_data.model;

import java.io.Serializable;

public class Ingredient implements Serializable {
    // ============================================================
    // fields
    private String name;
    private String quantity;
    private boolean userHasIngred;

    // ============================================================
    // constructors

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
        this.userHasIngred = false;
    }

    // ============================================================
    // default getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
