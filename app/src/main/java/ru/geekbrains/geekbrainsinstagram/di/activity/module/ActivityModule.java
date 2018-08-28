package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Navigator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer.MainPresenter;

@Module
public final class ActivityModule {

    @ActivityScope
    @Provides
    INavigator provideNavigator(){
        return new Navigator();
    }

    @ActivityScope
    @Provides
    IMainPresenter provideMainPresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        MainApplication.getApp().getComponentsManager().getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }
}
