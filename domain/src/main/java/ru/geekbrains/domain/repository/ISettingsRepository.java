package ru.geekbrains.domain.repository;

import io.reactivex.Single;

public interface ISettingsRepository {

    Single<Boolean> shouldChangeTheme(String theme);

    Single<String> getCurrentTheme();
}
