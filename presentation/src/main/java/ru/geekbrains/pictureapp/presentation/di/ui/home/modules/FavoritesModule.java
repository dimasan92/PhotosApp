package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesPresenterImpl;

@Module
public interface FavoritesModule {

    @HomeScope
    @Binds
    FavoritesPresenter provideFavoritesPresenter(final FavoritesPresenterImpl presenter);
}
