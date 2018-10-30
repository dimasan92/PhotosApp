package ru.geekbrains.geekbrainsinstagram.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenterImpl;

@Module
public interface CameraPhotosModule {

    @HomeScope @Binds CameraPhotosPresenter provideCameraPhotosPresenter(final CameraPhotosPresenterImpl presenter);
}
