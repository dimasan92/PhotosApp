package ru.geekbrains.pictureapp.domain.repository;

import io.reactivex.Single;
import ru.geekbrains.pictureapp.domain.model.AppThemeModel;

public interface SettingsRepository {

    Single<Boolean> changeTheme(final AppThemeModel themeModel);

    Single<AppThemeModel> getCurrentTheme();
}
