package ru.geekbrains.geekbrainsinstagram.di.fragment.cameraphotos;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosPresenterImpl;

@Module
public interface CameraPhotosFragmentModule {

    @CameraPhotosFragmentScope
    @Binds
    CameraPhotosPresenter provideCameraPhotosPresenter(CameraPhotosPresenterImpl presenter);
}
