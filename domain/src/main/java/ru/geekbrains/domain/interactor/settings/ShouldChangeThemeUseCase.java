package ru.geekbrains.domain.interactor.settings;

import io.reactivex.Single;
import ru.geekbrains.domain.repository.SettingsRepository;

public final class ChangeThemeUseCase {

    private final SettingsRepository settingsRepository;

    public ChangeThemeUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Boolean> execute(int themeId){
        return settingsRepository.changeTheme(themeId);
    }
}
