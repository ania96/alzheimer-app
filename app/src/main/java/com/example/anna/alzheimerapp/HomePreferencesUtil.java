package com.example.anna.alzheimerapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class HomePreferencesUtil {

    public static final String HOME_PREFS = "HOME_PREFS";
    public static final String HOME_LAT = "HOME_LAT";
    public static final String HOME_LNG = "HOME_LNG";

    public static final void putFloat(Activity activity, String key, float value) {
        SharedPreferences.Editor edit = activity.getSharedPreferences(HOME_PREFS, Context.MODE_PRIVATE).edit();
        edit.putFloat(key, value);
        // zapisuje w danych aplikacji
        edit.apply();
    }

    public static final float getFloat(Activity activity, String key, float defaultValue) {
        return activity.getSharedPreferences(HOME_PREFS, Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }



}
