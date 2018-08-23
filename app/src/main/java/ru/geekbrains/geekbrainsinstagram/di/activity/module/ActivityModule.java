package ru.geekbrains.geekbrainsinstagram.di.activity.module;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.geekbrainsinstagram.MainApplication;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.MainPresenter;

@Module
public final class ActivityModule {

    @ActivityScope
    @Provides
    IMainPresenter providePresenter() {
        MainPresenter mainPresenter = new MainPresenter();
        MainApplication.getApp().getComponentsManager().getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }
}
