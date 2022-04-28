package com.abdn.cooktoday;

/**
 * CookTodaySettings
 *
 * This class holds important settings for the application.
 */
public final class CookTodaySettings {

    // set to true if CookToday server is running locally
    public static final boolean serverRunningLocally = false;

    public static final String packageName = R.class.getPackage().getName();

    public static final String noneStr = "none";

    public static String getDataFolder() {
        return "data/data/" + packageName + "/";
    }
}
