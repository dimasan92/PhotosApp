package ru.geekbrains.geekbrainsinstagram.di.activity.main;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.IMainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenter;

@Module
public interface MainActivityModule {

    @Binds
    IMainPresenter provideMainPresenter(MainPresenter mainPresenter);
}
