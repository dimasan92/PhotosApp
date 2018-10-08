package ru.geekbrains.geekbrainsinstagram.di.fragment.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.fragment.FragmentScope;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.FragmentToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IFragmentToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.IFavoritesPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos.FullscreenPhotosPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.fullscreenphotos.FullscreenPhotosPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.IAppThemePresenter;

@Module
public interface FragmentModule {

    @FragmentScope
    @Binds
    IAppThemePresenter provideAppThemePresenter(AppThemePresenter presenter);

    @FragmentScope
    @Binds
    IFavoritesPresenter provideFavoritesPresenter(FavoritesPresenter presenter);

    @FragmentScope
    @Binds
    FullscreenPhotosPresenter providePhotoDetailsPresenter(FullscreenPhotosPresenterImpl presenter);

    @FragmentScope
    @Binds
    IFragmentToFragmentMediator provideIFragmentToFragmentMediator(FragmentToFragmentMediator fragmentUtils);
}
