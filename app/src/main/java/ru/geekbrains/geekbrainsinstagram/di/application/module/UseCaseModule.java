package ru.geekbrains.geekbrainsinstagram.di.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.domain.interactor.photos.ChangeFavoriteStatusPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.DeletePersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.photos.GetPersonalPhotosUseCase;
import ru.geekbrains.domain.interactor.photos.SaveNewPersonalPhotoUseCase;
import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.domain.interactor.settings.ShouldChangeThemeUseCase;
import ru.geekbrains.domain.repository.IPhotosRepository;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Module
public final class UseCaseModule {

    @Singleton
    @Provides
    ShouldChangeThemeUseCase provideShouldChangeThemeUseCase(final ISettingsRepository repository) {
        return new ShouldChangeThemeUseCase(repository);
    }

    @Singleton
    @Provides
    GetCurrentThemeUseCase provideGetCurrentThemeUseCase(final ISettingsRepository repository) {
        return new GetCurrentThemeUseCase(repository);
    }

    @Singleton
    @Provides
    SaveNewPersonalPhotoUseCase providesNewPersonalPhotoUseCase(final IPhotosRepository repository) {
        return new SaveNewPersonalPhotoUseCase(repository);
    }

    @Singleton
    @Provides
    GetPersonalPhotosUseCase provideGetPersonalPhotosUseCase(final IPhotosRepository repository) {
        return new GetPersonalPhotosUseCase(repository);
    }

    @Singleton
    @Provides
    ChangeFavoriteStatusPersonalPhotoUseCase provideChangeFavoriteStatusPersonalPhotoUseCase
            (final IPhotosRepository repository) {
        return new ChangeFavoriteStatusPersonalPhotoUseCase(repository);
    }

    @Singleton
    @Provides
    DeletePersonalPhotoUseCase provideDeletePersonalPhotoUseCase(final IPhotosRepository repository) {
        return new DeletePersonalPhotoUseCase(repository);
    }
}
