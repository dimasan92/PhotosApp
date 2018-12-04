package ru.geekbrains.pictureapp.presentation.di.application.modules;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import ru.geekbrains.pictureapp.presentation.ui.updater.Updater;
import ru.geekbrains.pictureapp.presentation.ui.updater.UpdaterImpl;

@Module
public interface UpdaterModule {

    @Singleton
    @Binds
    Updater provideUpdater(final UpdaterImpl updater);
}
