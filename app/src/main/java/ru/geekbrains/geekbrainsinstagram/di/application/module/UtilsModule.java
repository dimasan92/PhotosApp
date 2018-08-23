package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.data.util.PrefUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.R;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtils;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtils;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtilsImpl;

@Module
public final class UtilsModule {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";
    private static final int DEFAULT_THEME = R.style.BlueAppTheme;

    @Singleton
    @Provides
    PrefUtils providePrefUtils(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
        return new PrefUtilsImpl(preferences, DEFAULT_THEME);
    }

    @Singleton
    @Provides
    FilesUtils provideFilesUtils(Context context) {
        return new FilesUtilsImpl(context);
    }

    @Singleton
    @Provides
    PictureUtils providePictureUtils() {
        return new PictureUtilsImpl();
    }
}
