package com.abdn.cooktoday.local_data;

import com.abdn.cooktoday.local_data.model.User;

public class LoggedInUser {
    private static LoggedInUser instance;
    private LoggedInUser() {
        fistName = "";
        lastName = "";
        serverID = "";
        sessionID = "";
        role = "";
        email = "";
    }
    public static LoggedInUser user() {
        if (instance == null)
            instance = new LoggedInUser();
        return instance;
    }

    private String fistName;
    private String lastName;
    private String serverID;
    private String sessionID;
    private String role;
    private String email;

    public void setUser(User user) {
        fistName = user.getFirstName();
        lastName = user.getLastName();
        role = user.getRole();
        email = user.getEmail();
        serverID = user.getServerId();
        sessionID = user.getSessionId();
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}