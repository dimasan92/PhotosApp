package ru.geekbrains.geekbrainsinstagram.di.ui.home.modules;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.HomeNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.HomeNavigatorImpl;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomePresenterImpl;

@Module
public interface HomeModule {

    @HomeScope @Binds HomeNavigator provideHomeNavigator(final HomeNavigatorImpl navigator);

    @HomeScope @Binds HomePresenter provideAppThemePresenter(final HomePresenterImpl presenter);
}
