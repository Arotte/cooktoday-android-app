package com.abdn.cooktoday.api_connection;

import com.abdn.cooktoday.CookTodaySettings;

public final class APIEndpoints {
    private static final String BASE_DEPLOYED = "https://cooktoday-api.herokuapp.com/api/"; // DEPLOYED SERVER
    private static final String BASE_EMULATOR = "http://10.0.2.2:8000/api/"; // WHEN SERVER RUNS ON LOCALHOST

    public static final String AUTH = "auth/";
    public static final String USER = "user/";

    public static final String getBase() {
        return CookTodaySettings.serverRunningLocally ? BASE_EMULATOR : BASE_DEPLOYED;
    }
}
