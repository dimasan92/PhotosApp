package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.ui.maincontainer.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.maincontainer.MainPresenter;
import ru.geekbrains.geekbrainsinstagram.util.ActivityUtils;
import ru.geekbrains.geekbrainsinstagram.util.IActivityUtils;

@Module
public interface ActivityModule {

    @ActivityScope
    @Binds
    INavigator provideNavigator(Navigator navigator);

    @ActivityScope
    @Binds
    IActivityUtils provideActivityUtils(ActivityUtils activityUtils);

    @ActivityScope
    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);
}
