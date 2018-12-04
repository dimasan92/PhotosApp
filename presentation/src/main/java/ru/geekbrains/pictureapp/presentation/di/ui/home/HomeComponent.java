package ru.geekbrains.pictureapp.presentation.di.ui.home;

import dagger.Subcomponent;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.PhotosModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.FavoritesModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.HomeModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.OnlinePicturesModule;
import ru.geekbrains.pictureapp.presentation.di.ui.home.modules.SavedPicturesModule;
import ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures.OnlinePicturesFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.favorites.FavoritesFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomeFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesFragment;
import ru.geekbrains.pictureapp.presentation.ui.screens.search.SearchFragment;

@HomeScope
@Subcomponent(modules = {
        PhotosModule.class,
        FavoritesModule.class,
        HomeModule.class,
        OnlinePicturesModule.class,
        SavedPicturesModule.class
})
public interface HomeComponent {

    void inject(final HomeFragment homeFragment);

    void inject(final SearchFragment searchFragment);

    void inject(final OnlinePicturesFragment onlinePicturesFragment);

    void inject(final SavedPicturesFragment savedPicturesFragment);

    void inject(final PhotosFragment photosFragment);

    void inject(final FavoritesFragment favoritesFragment);
}
