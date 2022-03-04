package com.abdn.cooktoday.local_data.model;

public class Ingredient {
    // ============================================================
    // fields
    private String name;
    private String quantity;

    // ============================================================
    // constructors

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
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
