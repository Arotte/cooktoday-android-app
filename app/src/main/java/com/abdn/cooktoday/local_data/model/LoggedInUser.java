package com.abdn.cooktoday.local_data.model;



/**
 * Data class that captures user information for
 * logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private final String userId;
    private final String displayName;

    private String serverUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;


    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(String userId, String displayName, String serverUserId, String firstName, String lastName, String email, String role) {
        this.userId = userId;
        this.displayName = displayName;
        this.serverUserId = serverUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getServerUserId() {
        return serverUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}