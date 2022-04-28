package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.CookTodaySettings;

/**
 * APIEndpoints
 *
 * A final class that only contains static fields.
 * It contains the API endpoints that are used by the application.
 */
public final class APIEndpoints {
    private static final String BASE_DEPLOYED = "https://cooktodayapp.herokuapp.com/api/"; // DEPLOYED SERVER
    private static final String BASE_EMULATOR = "http://10.0.2.2:8000/api/"; // WHEN SERVER RUNS ON LOCALHOST

    public static final String AUTH = "auth/";
    public static final String USER = "user/";

    public static final String getBase() {
        return CookTodaySettings.serverRunningLocally ? BASE_EMULATOR : BASE_DEPLOYED;
    }
}
