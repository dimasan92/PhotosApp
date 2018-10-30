package ru.geekbrains.geekbrainsinstagram.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.data.util.FileUtils;
import ru.geekbrains.data.util.FileUtilsImpl;
import ru.geekbrains.data.util.NetworkUtils;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.data.util.PrefUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtils;
import ru.geekbrains.geekbrainsinstagram.util.CameraUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.ContentUtils;
import ru.geekbrains.geekbrainsinstagram.util.ContentUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtils;
import ru.geekbrains.geekbrainsinstagram.util.LayoutUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.NetworkUtilsImpl;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtils;
import ru.geekbrains.geekbrainsinstagram.util.PictureUtilsImpl;

@Module
public interface UtilsModule {

    @Singleton
    @Binds
    PrefUtils providePrefUtils(final PrefUtilsImpl prefUtils);

    @Singleton
    @Binds
    ContentUtils provideContentUtils(final ContentUtilsImpl contentUtilsImpl);

    @Singleton
    @Binds
    PictureUtils providePictureUtils(final PictureUtilsImpl pictureUtilsImpl);

    @Singleton
    @Binds
    CameraUtils provideCameraUtils(final CameraUtilsImpl cameraUtilsImpl);

    @Singleton
    @Binds
    LayoutUtils provideLayoutUtils(final LayoutUtilsImpl layoutUtilsImpl);

    @Singleton
    @Binds
    FileUtils provideFilesUtils(final FileUtilsImpl filesUtilsImpl);

    @Singleton
    @Binds
    NetworkUtils provideNetworkUtils(final NetworkUtilsImpl networkUtils);
}
