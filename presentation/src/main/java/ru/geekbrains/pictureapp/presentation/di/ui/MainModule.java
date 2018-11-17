package ru.geekbrains.pictureapp.presentation.di.ui;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.ui.container.MainPresenter;
import ru.geekbrains.pictureapp.presentation.ui.container.MainPresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediator;
import ru.geekbrains.pictureapp.presentation.ui.container.mediator.ContainerToContentMediatorImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigatorImpl;

@Module
public interface MainModule {

    @MainScope @Binds MainNavigator provideMainNavigator(final MainNavigatorImpl navigator);

    @MainScope @Binds MainPresenter provideMainPresenter(final MainPresenterImpl presenter);

    @MainScope @Binds ContainerToContentMediator provideMediator(final ContainerToContentMediatorImpl mediator);
}
