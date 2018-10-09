package ru.geekbrains.geekbrainsinstagram.di.activity.settings;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenter;
import ru.geekbrains.geekbrainsinstagram.ui.containers.settings.SettingsPresenterImpl;

@Module
public interface SettingsActivityModule {

    @Binds
    SettingsPresenter provideMainPresenter(SettingsPresenterImpl settingsPresenter);
}
