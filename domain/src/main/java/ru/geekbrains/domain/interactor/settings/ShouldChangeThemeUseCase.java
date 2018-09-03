package ru.geekbrains.domain.interactor.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Singleton
public final class ShouldChangeThemeUseCase {

    private final ISettingsRepository settingsRepository;

    @Inject
    ShouldChangeThemeUseCase(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Boolean> execute(String theme) {
        return settingsRepository.shouldChangeTheme(theme);
    }
}
