package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.ActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.ui.maincontainer.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.maincontainer.MainPresenter;

@Module
public interface ActivityModule {

    @ActivityScope
    @Binds
    INavigator provideNavigator(Navigator navigator);

    @ActivityScope
    @Binds
    IActivityToFragmentMediator provideActivityUtils(ActivityToFragmentMediator activityUtils);

    @ActivityScope
    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);
}
