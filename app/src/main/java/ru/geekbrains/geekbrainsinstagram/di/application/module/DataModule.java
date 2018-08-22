package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.data.settings.SettingsRepositoryImpl;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.domain.repository.SettingsRepository;

@Module
public final class DataModule {

    @Singleton
    @Provides
    SettingsRepository providesSettingsRepository(final PrefUtils prefUtils) {
        return new SettingsRepositoryImpl(prefUtils);
    }
}
