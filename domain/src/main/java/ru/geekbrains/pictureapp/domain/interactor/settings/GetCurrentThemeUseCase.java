package ru.geekbrains.pictureapp.domain.interactor.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.AppThemeModel;
import ru.geekbrains.pictureapp.domain.repository.SettingsRepository;

@Singleton
public final class GetCurrentThemeUseCase {

    private final SettingsRepository settingsRepository;

    @Inject GetCurrentThemeUseCase(final SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<AppThemeModel> execute() {
        return settingsRepository.getCurrentTheme();
    }
}
