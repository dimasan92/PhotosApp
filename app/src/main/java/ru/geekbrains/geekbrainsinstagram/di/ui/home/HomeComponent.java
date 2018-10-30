package ru.geekbrains.geekbrainsinstagram.di.ui.home;

import dagger.Subcomponent;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.modules.CameraPhotosModule;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.modules.FavoritesModule;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.modules.HomeModule;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.modules.OnlineSearchModule;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.modules.SavedSearchModule;
import ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotosFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomeFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.onlinesearch.OnlineSearchFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchFragment;
import ru.geekbrains.geekbrainsinstagram.ui.screens.search.SearchFragment;

@HomeScope
@Subcomponent(modules = {HomeModule.class,
        OnlineSearchModule.class,
        SavedSearchModule.class,
        CameraPhotosModule.class,
        FavoritesModule.class})
public interface HomeComponent {

    void inject(final HomeFragment homeFragment);

    void inject(final SearchFragment searchFragment);

    void inject(final OnlineSearchFragment onlineSearchFragment);

    void inject(final SavedSearchFragment savedSearchFragment);

    void inject(final CameraPhotosFragment cameraPhotosFragment);

    void inject(final FavoritesFragment favoritesFragment);
}
