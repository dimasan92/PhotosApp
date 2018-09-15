package ru.geekbrains.domain.interactor.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.domain.repository.SettingsRepository;

@Singleton
public final class ShouldChangeThemeUseCase {

    private final SettingsRepository settingsRepository;

    @Inject
    ShouldChangeThemeUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Boolean> execute(final AppThemeModel theme) {
        return settingsRepository.shouldChangeTheme(theme);
    }
}
