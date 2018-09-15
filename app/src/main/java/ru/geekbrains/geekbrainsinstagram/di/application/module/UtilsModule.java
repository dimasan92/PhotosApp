package ru.geekbrains.geekbrainsinstagram.di.application.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.util.FilesUtilsImpl;
import ru.geekbrains.data.util.FilesUtils;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.data.util.PrefUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.ContentUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.ContentUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtilsImpl;

@Module
public abstract class UtilsModule {

    private static final String INSTAGRAM_PREFERENCES = "instagram_preferences";

    @Singleton
    @Provides
    static PrefUtils providePrefUtils(Context context) {
        SharedPreferences preferences =
                context.getSharedPreferences(INSTAGRAM_PREFERENCES, Context.MODE_PRIVATE);
        return new PrefUtilsImpl(preferences);
    }

    @Singleton
    @Binds
    abstract ContentUtils provideContentUtils(ContentUtilsImpl contentUtilsImpl);

    @Singleton
    @Binds
    abstract PictureUtils providePictureUtils(PictureUtilsImpl pictureUtilsImpl);

    @Singleton
    @Binds
    abstract CameraUtils provideCameraUtils(CameraUtilsImpl cameraUtilsImpl);

    @Singleton
    @Binds
    abstract LayoutUtils provideLayoutUtils(LayoutUtilsImpl layoutUtilsImpl);

    @Singleton
    @Binds
    abstract FilesUtils provideFilesUtils(FilesUtilsImpl filesUtilsImpl);
}
