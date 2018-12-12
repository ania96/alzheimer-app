package com.example.anna.alzheimerapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class HomePreferencesUtil {

    public static final String HOME_PREFS = "HOME_PREFS";
    public static final String HOME = "HOME";

    public static final void putString(Activity activity, String key, String value) {
        SharedPreferences.Editor edit = activity.getSharedPreferences(HOME_PREFS, Context.MODE_PRIVATE).edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static final String getString(Activity activity, String key, String defaultValue) {
        return activity.getSharedPreferences(HOME_PREFS, Context.MODE_PRIVATE).getString(key, defaultValue);
    }



}
