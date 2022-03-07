package com.abdn.cooktoday.local_data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the local cache using Android's
 * SharedPreferences feature.
 *
 * NOTE:
 * The SharedPreferences XML is stored in
 * `data/data/com.abdn.cooktoday/shared_prefs/com.abdn.cooktoday.xml`
 */
public class Cache {
    private static SharedPreferences mSharedPref;

    public static final String KEY_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static final String KEY_USER_EMAIL = "LOGGED_IN_USER_EMAIL";
    public static final String KEY_USER_ID = "LOGGED_IN_USER_SERVER_ID";
    public static final String KEY_USER_FNAME = "LOGGED_IN_USER_FIRST_NAME";
    public static final String KEY_USER_LNAME = "LOGGED_IN_USER_LAST_NAME";
    public static final String KEY_USER_ROLE = "LOGGED_IN_USER_ROLE";
    public static final String KEY_USER_SESSID = "LOGGED_IN_USER_SESSION_ID";

    private Cache() {
    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read_string(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write_string(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static boolean read_bool(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write_bool(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static Integer read_int(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write_int(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }
}