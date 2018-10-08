package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.ActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.mediator.IActivityToFragmentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;

@Module
public interface ActivityModule {

    @ActivityScope
    @Binds
    INavigator provideNavigator(Navigator navigator);

    @ActivityScope
    @Binds
    IActivityToFragmentMediator provideActivityToFragmentMediator(ActivityToFragmentMediator activityUtils);

    @ActivityScope
    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);

    @ActivityScope
    @Binds
    SettingsPresenter provideSettingsPresenter(SettingsPresenterImpl settingsPresenter);
}
