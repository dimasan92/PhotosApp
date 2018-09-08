package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.util.IPrefUtils;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.ContentUtils;
import ru.geekbrains.geekbrainsinstagram.util.ICameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.IContentUtils;
import ru.geekbrains.geekbrainsinstagram.util.ILayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.IPictureUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;

@Module
public abstract class UtilsModule {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";
    private static final String DEFAULT_THEME = AppTheme.BLUE_THEME.toString();

    @Singleton
    @Provides
    static IPrefUtils providePrefUtils(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
        return new PrefUtils(preferences, DEFAULT_THEME);
    }

    @Singleton
    @Binds
    abstract IContentUtils provideContentUtils(ContentUtils filesContentUtils);

    @Singleton
    @Binds
    abstract IPictureUtils providePictureUtils(PictureUtils pictureUtils);

    @Singleton
    @Binds
    abstract ICameraUtils provideCameraUtils(CameraUtils cameraUtils);

    @Singleton
    @Binds
    abstract ILayoutUtils provideLayoutUtils(LayoutUtils layoutUtils);
}
