package com.inficta.alokitsupport.Base;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences sharedPreferences;
    Context context;

    public Preferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences("RekipPreFence", Context.MODE_PRIVATE);
        this.context = context;
    }

    public String getStringPreference(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBooleanPreference(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean putPrefString(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    public void deletePreference(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key).apply();
    }
}

