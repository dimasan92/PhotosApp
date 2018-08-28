package ru.geekbrains.data.util;

import android.content.SharedPreferences;

import androidx.annotation.StyleRes;


public final class PrefUtilsImpl implements PrefUtils {

    private static final String PREF_CURRENT_THEME = "current_application_theme";

    private final SharedPreferences sharedPreferences;
    private final int defaultTheme;

    public PrefUtilsImpl(SharedPreferences sharedPreferences, @StyleRes int defaultTheme) {
        this.sharedPreferences = sharedPreferences;
        this.defaultTheme = defaultTheme;
    }

    @Override
    public void saveCurrentTheme(@StyleRes int themeId) {
        getEditor()
                .putInt(PREF_CURRENT_THEME, themeId)
                .apply();
    }

    @Override
    public int currentTheme() {
        return sharedPreferences
                .getInt(PREF_CURRENT_THEME, defaultTheme);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
