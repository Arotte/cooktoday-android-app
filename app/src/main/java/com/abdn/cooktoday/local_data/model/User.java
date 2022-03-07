package com.abdn.cooktoday.local_data.model;

public class User {
    private String email;
    private String serverId;
    private String firstName;
    private String lastName;
    private String role;
    private String sessionId;

    public User() {
    }

    public User(String email, String serverId, String firstName, String lastName, String role, String sessionId) {
        this.email = email;
        this.serverId = serverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
