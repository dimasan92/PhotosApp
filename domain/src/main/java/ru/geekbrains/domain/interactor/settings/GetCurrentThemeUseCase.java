package ru.geekbrains.domain.interactor.settings;

import ru.geekbrains.domain.repository.SettingsRepository;

public final class GetCurrentThemeUseCase {

    private final SettingsRepository settingsRepository;

    public GetCurrentThemeUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public int execute() {
        return settingsRepository.getCurrentTheme();
    }
}
