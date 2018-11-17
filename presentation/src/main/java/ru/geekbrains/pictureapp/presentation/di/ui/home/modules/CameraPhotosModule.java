package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotosPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotosPresenterImpl;

@Module
public interface CameraPhotosModule {

    @HomeScope @Binds CameraPhotosPresenter provideCameraPhotosPresenter(final CameraPhotosPresenterImpl presenter);
}
