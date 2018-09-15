package ru.geekbrains.domain.repository;

import io.reactivex.Single;
import ru.geekbrains.domain.model.AppThemeModel;

public interface SettingsRepository {

    Single<Boolean> shouldChangeTheme(final AppThemeModel theme);

    Single<AppThemeModel> getCurrentTheme();
}
