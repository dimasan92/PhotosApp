package ru.geekbrains.geekbrainsinstagram.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ru.geekbrains.geekbrainsinstagram.R;

public final class SettingsPrefsUtils {

    private static final String SETTINGS_PREFERENCES = "settings preferences";

    private static final String CURRENT_THEME = "current theme";

    private SettingsPrefsUtils() {
    }

    public static void saveCurrentTheme(Context context, int themeId) {
        getEditor(context)
                .putInt(CURRENT_THEME, themeId)
                .apply();
    }

    public static int getCurrentTheme(Context context) {
        return getPref(context)
                .getInt(CURRENT_THEME, R.style.BlueAppTheme);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPref(context).edit();
    }

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }
}
