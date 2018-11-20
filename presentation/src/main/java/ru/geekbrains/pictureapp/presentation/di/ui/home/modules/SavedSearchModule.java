package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch.SavedSearchPresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.savedsearch.SavedSearchPresenterImpl;

@Module
public interface SavedSearchModule {

    @HomeScope
    @Binds
    SavedSearchPresenter provideCameraPhotosPresenter(final SavedSearchPresenterImpl presenter);
}
