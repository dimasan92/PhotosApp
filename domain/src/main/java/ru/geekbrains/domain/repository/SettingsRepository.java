package ru.geekbrains.domain.repository;

import io.reactivex.Single;

public interface SettingsRepository {

    Single<Boolean> changeTheme(int themeId);

    Single<Integer> getCurrentTheme();
}
