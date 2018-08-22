package ru.geekbrains.data.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StyleRes;


public final class PrefUtilImpl implements PrefUtils {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";

    private static final String PREF_CURRENT_THEME = "current_application_theme";

    @Override
    public void saveCurrentTheme(Context context, @StyleRes int themeId) {
        getEditor(context)
                .putInt(PREF_CURRENT_THEME, themeId)
                .apply();
    }

    @Override
    public int currentTheme(Context context, @StyleRes int defaultTheme) {
        return getPref(context)
                .getInt(PREF_CURRENT_THEME, defaultTheme);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return getPref(context).edit();
    }

    private SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
    }
}
