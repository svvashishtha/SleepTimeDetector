package app.sleep.detect.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferencesProvider {
    SharedPreferences sharedPreferences;
    String APP_PREFRENCES = "sleep_preferences";

    @Inject
    public SharedPreferencesProvider(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFRENCES, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void putData(String key, boolean flag) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, flag);
        edit.apply();
    }

    public boolean getBooleanData(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }
}
