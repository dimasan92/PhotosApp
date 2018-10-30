package ru.geekbrains.geekbrainsinstagram.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch.SavedSearchPresenterImpl;

@Module
public interface SavedSearchModule {

    @HomeScope @Binds SavedSearchPresenter provideCameraPhotosPresenter(final SavedSearchPresenterImpl presenter);
}
