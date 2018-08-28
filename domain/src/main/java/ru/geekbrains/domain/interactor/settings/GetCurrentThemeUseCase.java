package ru.geekbrains.domain.interactor.settings;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.ISettingsRepository;

public final class GetCurrentThemeUseCase {

    private final ISettingsRepository settingsRepository;

    public GetCurrentThemeUseCase(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Integer> execute() {
        return settingsRepository.getCurrentTheme();
    }
}
