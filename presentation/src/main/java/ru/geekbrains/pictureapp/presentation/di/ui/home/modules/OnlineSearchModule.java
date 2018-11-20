package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchPresenterImpl;

@Module
public interface OnlineSearchModule {

    @HomeScope
    @Binds
    OnlineSearchPresenter provideCameraPhotosPresenter(final OnlineSearchPresenterImpl presenter);
}
