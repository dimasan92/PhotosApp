package ru.geekbrains.data.util;

import android.content.SharedPreferences;

public final class PrefUtilsImpl implements PrefUtils {

    private static final String PREF_CURRENT_THEME = "current_application_theme";

    private final SharedPreferences sharedPreferences;

    public PrefUtilsImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveCurrentTheme(final String theme) {
        getEditor()
                .putString(PREF_CURRENT_THEME, theme)
                .apply();
    }

    @Override
    public String currentTheme(final String defaultTheme) {
        return sharedPreferences
                .getString(PREF_CURRENT_THEME, defaultTheme);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
