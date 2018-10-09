package ru.geekbrains.geekbrainsinstagram.di.activity.main;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenterImpl;

@Module
public interface MainActivityModule {

    @Binds
    MainPresenter provideMainPresenter(MainPresenterImpl mainPresenter);
}
