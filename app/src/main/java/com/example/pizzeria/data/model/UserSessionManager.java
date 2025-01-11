package com.example.pizzeria.data.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_display_name";

    private static UserSessionManager instance;
    private SharedPreferences prefs;

    private UserSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context.getApplicationContext());
        }
        return instance;
    }

    // Sprawdza, czy użytkownik jest zalogowany
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Pobiera bieżącego zalogowanego użytkownika
    public LoggedInUser getLoggedInUser() {
        if (isLoggedIn()) {
            int userId = prefs.getInt(KEY_USER_ID, -1);
            String userName = prefs.getString(KEY_USER_NAME, "Unknown");
            return new LoggedInUser(userId, userName);
        }
        return null;
    }

    // Zapisuje dane użytkownika w SharedPreferences po zalogowaniu
    public void saveUserSession(LoggedInUser user) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, user.getUserId());
        editor.putString(KEY_USER_NAME, user.getUserName());
        editor.apply();
    }

    // Czyści dane po wylogowaniu
    public void clearSession() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
