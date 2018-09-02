package ru.geekbrains.domain.interactor.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Singleton
public final class GetCurrentThemeUseCase {

    private final ISettingsRepository settingsRepository;

    @Inject
    GetCurrentThemeUseCase(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<String> execute() {
        return settingsRepository.getCurrentTheme();
    }
}
