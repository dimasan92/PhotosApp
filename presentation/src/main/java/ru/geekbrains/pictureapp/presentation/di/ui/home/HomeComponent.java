package ru.geekbrains.pictureapp.presentation.di.ui.home;

import dagger.Subcomponent;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.CameraPhotosModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.FavoritesModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.FullscreenPhotosModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.HomeModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.OnlineSearchModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.SavedSearchModule;
import ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotosFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.fullscreenphotos.FullscreenPhotosFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomeFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinesearch.OnlineSearchFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch.SavedSearchFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.search.SearchFragment;

@HomeScope
@Subcomponent(modules = {
        CameraPhotosModule.class,
        FavoritesModule.class,
        FullscreenPhotosModule.class,
        HomeModule.class,
        OnlineSearchModule.class,
        SavedSearchModule.class
})
public interface HomeComponent {

    void inject(final HomeFragment homeFragment);

    void inject(final SearchFragment searchFragment);

    void inject(final OnlineSearchFragment onlineSearchFragment);

    void inject(final SavedSearchFragment savedSearchFragment);

    void inject(final CameraPhotosFragment cameraPhotosFragment);

    void inject(final FavoritesFragment favoritesFragment);

    void inject(final FullscreenPhotosFragment fullscreenPhotosFragment);
}
