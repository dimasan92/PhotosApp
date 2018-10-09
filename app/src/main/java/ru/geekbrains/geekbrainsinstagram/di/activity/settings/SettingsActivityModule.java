package ru.geekbrains.geekbrainsinstagram.di.activity.settings;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator.SettingsContainerToContentMediator;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.mediator.SettingsContainerToContentMediatorImpl;

@Module
public interface SettingsActivityModule {

    @SettingsActivityScope
    @Binds
    SettingsPresenter provideMainPresenter(SettingsPresenterImpl settingsPresenter);

    @SettingsActivityScope
    @Binds
    SettingsContainerToContentMediator provideMediator(SettingsContainerToContentMediatorImpl mediator);
}
