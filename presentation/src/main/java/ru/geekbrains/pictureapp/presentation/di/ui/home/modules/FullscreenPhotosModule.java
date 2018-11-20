package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.fullscreenphotos.FullscreenPhotosPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.fullscreenphotos.FullscreenPhotosPresenterImpl;

@Module
public interface FullscreenPhotosModule {

    @HomeScope
    @Binds
    FullscreenPhotosPresenter provideCameraPhotosPresenter(final FullscreenPhotosPresenterImpl presenter);
}
