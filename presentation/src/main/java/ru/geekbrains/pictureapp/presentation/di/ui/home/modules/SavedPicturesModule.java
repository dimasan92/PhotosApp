package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesPresenterImpl;

@Module
public interface SavedPicturesModule {

    @HomeScope
    @Binds
    SavedPicturesPresenter provideSavedPicturesPresenter(final SavedPicturesPresenterImpl presenter);
}
