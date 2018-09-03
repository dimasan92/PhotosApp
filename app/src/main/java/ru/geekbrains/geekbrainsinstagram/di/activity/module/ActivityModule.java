package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.MainPresenter;

@Module
public interface ActivityModule {

    @ActivityScope
    @Binds
    INavigator provideNavigator(Navigator navigator);

    @ActivityScope
    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);
}
