package ru.geekbrains.pictureapp.presentation.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.data.util.FileUtils;
import ru.geekbrains.pictureapp.data.util.FileUtilsImpl;
import ru.geekbrains.pictureapp.data.util.NetworkUtils;
import ru.geekbrains.pictureapp.data.util.NetworkUtilsImpl;
import ru.geekbrains.pictureapp.data.util.PrefUtils;
import ru.geekbrains.pictureapp.data.util.PrefUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.CameraUtils;
import ru.geekbrains.pictureapp.presentation.util.CameraUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.ContentUtils;
import ru.geekbrains.pictureapp.presentation.util.ContentUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtils;
import ru.geekbrains.pictureapp.presentation.util.LayoutUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.ParseUtils;
import ru.geekbrains.pictureapp.presentation.util.ParseUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.PictureUtils;
import ru.geekbrains.pictureapp.presentation.util.PictureUtilsImpl;
import ru.geekbrains.pictureapp.presentation.util.ViewUtils;
import ru.geekbrains.pictureapp.presentation.util.ViewUtilsImpl;

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

    @Singleton
    @Binds
    ParseUtils provideParseUtils(final ParseUtilsImpl parseUtils);

    @Singleton
    @Binds
    ViewUtils provideViewUtils(final ViewUtilsImpl viewUtils);
}
