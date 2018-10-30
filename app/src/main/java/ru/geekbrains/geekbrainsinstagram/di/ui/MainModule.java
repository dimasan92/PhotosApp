package ru.geekbrains.geekbrainsinstagram.di.ui;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.container.MainPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.container.MainPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.container.mediator.ContainerToContentMediatorImpl;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigatorImpl;

@Module
public interface MainModule {

    @MainScope @Binds MainNavigator provideMainNavigator(final MainNavigatorImpl navigator);

    @MainScope @Binds MainPresenter provideMainPresenter(final MainPresenterImpl presenter);

    @MainScope @Binds ContainerToContentMediator provideMediator(final ContainerToContentMediatorImpl mediator);
}
