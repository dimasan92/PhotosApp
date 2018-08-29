package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.util.IPrefUtils;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.utils.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.utils.FilesUtils;
import ru.geekbrains.geekbrainsinstagram.utils.ICameraUtils;
import ru.geekbrains.geekbrainsinstagram.utils.IFilesUtils;
import ru.geekbrains.geekbrainsinstagram.utils.ILayoutUtils;
import ru.geekbrains.geekbrainsinstagram.utils.IPictureUtils;
import ru.geekbrains.geekbrainsinstagram.utils.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.utils.PictureUtils;

@Module
public final class UtilsModule {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";
    private static final String DEFAULT_THEME = AppTheme.BLUE_THEME.toString();

    @Singleton
    @Provides
    IPrefUtils providePrefUtils(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
        return new PrefUtils(preferences, DEFAULT_THEME);
    }

    @Singleton
    @Provides
    IFilesUtils provideFilesUtils(Context context) {
        return new FilesUtils(context);
    }

    @Singleton
    @Provides
    IPictureUtils providePictureUtils() {
        return new PictureUtils();
    }

    @Singleton
    @Provides
    ICameraUtils provideCameraUtils(Context context, IFilesUtils filesUtils) {
        return new CameraUtils(context, filesUtils);
    }

    @Singleton
    @Provides
    ILayoutUtils provideILayoutUtils(Context context) {
        return new LayoutUtils(context);
    }
}
