package ru.geekbrains.pictureapp.domain.interactor.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.AppThemeModel;
import ru.geekbrains.pictureapp.domain.repository.SettingsRepository;

@Singleton
public final class ChangeThemeUseCase {

    private final SettingsRepository settingsRepository;

    @Inject
    ChangeThemeUseCase(final SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Single<Boolean> execute(final AppThemeModel themeModel) {
        return settingsRepository.changeTheme(themeModel);
    }
}
