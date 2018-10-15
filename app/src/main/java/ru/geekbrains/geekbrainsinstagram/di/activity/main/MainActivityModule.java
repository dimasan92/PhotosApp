package ru.geekbrains.geekbrainsinstagram.di.activity.main;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.MainPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator.MainContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.containers.main.mediator.MainContainerToContentMediatorImpl;

@Module
public interface MainActivityModule {

    @MainActivityScope
    @Binds
    MainPresenter provideMainPresenter(MainPresenterImpl mainPresenter);

    @MainActivityScope
    @Binds
    MainContainerToContentMediator provideMediator(MainContainerToContentMediatorImpl mediator);
}
