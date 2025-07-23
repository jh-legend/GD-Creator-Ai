package com.liveinaura.gdcreator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeUtils {
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String THEME_KEY = "Theme";

    public static void applyTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int theme = prefs.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_YES);
        AppCompatDelegate.setDefaultNightMode(theme);
    }

    public static void setTheme(Context context, int theme) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(THEME_KEY, theme);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(theme);
    }
}
