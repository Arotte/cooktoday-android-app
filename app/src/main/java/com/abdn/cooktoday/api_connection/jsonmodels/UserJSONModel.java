package com.abdn.cooktoday.api_connection.jsonmodels;

import com.google.gson.annotations.SerializedName;

public class UserJSONModel {

    // ====================================================
    // fields
    @SerializedName("email")
    private String email;
    @SerializedName("_id")
    private String id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("role")
    private String role;

    // ====================================================
    // constructors

    public UserJSONModel(String id, String firstName, String lastName, String email, String role) {
        this.email = email;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserJSONModel{" +
                "email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    // ====================================================
    // getters
    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

}
