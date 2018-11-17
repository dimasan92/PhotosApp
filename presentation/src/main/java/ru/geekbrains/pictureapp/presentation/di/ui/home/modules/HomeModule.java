package ru.geekbrains.pictureapp.presentation.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.navigator.HomeNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.HomeNavigatorImpl;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomePresenter;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomePresenterImpl;

@Module
public interface HomeModule {

    @HomeScope @Binds HomeNavigator provideHomeNavigator(final HomeNavigatorImpl navigator);

    @HomeScope @Binds HomePresenter provideAppThemePresenter(final HomePresenterImpl presenter);
}
