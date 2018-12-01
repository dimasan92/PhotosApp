package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesPresenterImpl;

@Module
public interface OnlinePicturesModule {

    @HomeScope
    @Binds
    OnlinePicturesPresenter provideOnlinePicturesPresenter(final OnlinePicturesPresenterImpl presenter);
}
