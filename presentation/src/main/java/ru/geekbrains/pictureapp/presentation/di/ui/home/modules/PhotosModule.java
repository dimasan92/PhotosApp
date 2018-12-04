package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosPresenterImpl;

@Module
public interface PhotosModule {

    @HomeScope
    @Binds
    PhotosPresenter providePhotosPresenter(final PhotosPresenterImpl presenter);
}
