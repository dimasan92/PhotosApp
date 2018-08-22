package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.data.util.PrefUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.R;

@Module
public final class UtilsModule {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";
    private static final int DEFAULT_THEME = R.style.BlueAppTheme;

    private final SharedPreferences sharedPreferences;

    public UtilsModule(Context context) {
        sharedPreferences = context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    PrefUtils providePrefUtils() {
        return new PrefUtilsImpl(sharedPreferences, DEFAULT_THEME);
    }
}
