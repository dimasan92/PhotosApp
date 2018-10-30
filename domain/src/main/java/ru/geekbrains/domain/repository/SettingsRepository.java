package ru.geekbrains.domain.repository;

import io.reactivex.Single;
import ru.geekbrains.domain.model.AppThemeModel;

public interface SettingsRepository {

    Single<Boolean> changeTheme(final AppThemeModel themeModel);

    Single<AppThemeModel> getCurrentTheme();
}
