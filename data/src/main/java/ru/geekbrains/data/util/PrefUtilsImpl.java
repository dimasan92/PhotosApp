package ru.geekbrains.data.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class PrefUtilsImpl implements PrefUtils {

    private static final String GEEK_BRAINS_PHOTOS = "geek_brains_photos";
    private static final String PREF_CURRENT_THEME = "current_application_theme";

    private final SharedPreferences sharedPreferences;

    @Inject PrefUtilsImpl(final Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(GEEK_BRAINS_PHOTOS, Context.MODE_PRIVATE);
    }

    @Override public void saveCurrentTheme(final String theme) {
        getEditor()
                .putString(PREF_CURRENT_THEME, theme)
                .apply();
    }

    @Override public String getCurrentTheme(final String defaultTheme) {
        return sharedPreferences
                .getString(PREF_CURRENT_THEME, defaultTheme);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }
}
