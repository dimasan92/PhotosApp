package ru.geekbrains.domain.interactor.settings;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.SettingsRepository;

public final class GetCurrentThemeUseCase {

    private final SettingsRepository settingsRepository;

    public GetCurrentThemeUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Integer> execute() {
        return settingsRepository.getCurrentTheme();
    }
}
