package ru.geekbrains.geekbrainsinstagram.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch.OnlineSearchPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch.OnlineSearchPresenterImpl;

@Module
public interface OnlineSearchModule {

    @HomeScope @Binds OnlineSearchPresenter provideCameraPhotosPresenter(final OnlineSearchPresenterImpl presenter);
}
