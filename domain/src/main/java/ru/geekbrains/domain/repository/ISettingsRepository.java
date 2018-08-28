package ru.geekbrains.domain.repository;

import io.reactivex.Single;

public interface ISettingsRepository {

    Single<Boolean> shouldChangeTheme(int themeId);

    Single<Integer> getCurrentTheme();
}
