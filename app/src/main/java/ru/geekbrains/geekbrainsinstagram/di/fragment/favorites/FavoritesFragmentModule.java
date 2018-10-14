package ru.geekbrains.geekbrainsinstagram.di.fragment.favorites;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenterImpl;

@Module
public interface FavoritesFragmentModule {

    @FavoritesFragmentScope
    @Binds
    FavoritesPresenter provideCameraPhotosPresenter(FavoritesPresenterImpl presenter);
}
