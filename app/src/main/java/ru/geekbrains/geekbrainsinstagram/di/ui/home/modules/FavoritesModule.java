package ru.geekbrains.geekbrainsinstagram.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenterImpl;

@Module
public interface FavoritesModule {

    @HomeScope
    @Binds
    FavoritesPresenter provideFavoritesPresenter(final FavoritesPresenterImpl presenter);
}
