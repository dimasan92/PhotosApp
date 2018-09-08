package ru.geekbrains.domain.repository;

import io.reactivex.Single;
import ru.geekbrains.domain.model.AppThemeModel;

public interface ISettingsRepository {

    Single<Boolean> shouldChangeTheme(AppThemeModel theme);

    Single<AppThemeModel> getCurrentTheme();
}
