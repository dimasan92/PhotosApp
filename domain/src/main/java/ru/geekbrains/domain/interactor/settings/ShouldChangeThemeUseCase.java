package ru.geekbrains.domain.interactor.settings;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.ISettingsRepository;

public final class ShouldChangeThemeUseCase {

    private final ISettingsRepository settingsRepository;

    public ShouldChangeThemeUseCase(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Boolean> execute(int themeId){
        return settingsRepository.shouldChangeTheme(themeId);
    }
}
