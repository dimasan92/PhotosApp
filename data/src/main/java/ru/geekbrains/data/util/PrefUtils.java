package ru.geekbrains.data.util;

import android.content.SharedPreferences;

public final class PrefUtils implements IPrefUtils {

    private static final String PREF_CURRENT_THEME = "current_application_theme";

    private final SharedPreferences sharedPreferences;
    private final String defaultTheme;

    public PrefUtils(SharedPreferences sharedPreferences, String defaultTheme) {
        this.sharedPreferences = sharedPreferences;
        this.defaultTheme = defaultTheme;
    }

    @Override
    public void saveCurrentTheme(String theme) {
        getEditor()
                .putString(PREF_CURRENT_THEME, theme)
                .apply();
    }

    @Override
    public String currentTheme() {
        return sharedPreferences
                .getString(PREF_CURRENT_THEME, defaultTheme);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
