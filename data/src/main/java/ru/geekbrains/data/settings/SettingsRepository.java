package ru.geekbrains.data.settings;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.util.IPrefUtils;
import ru.geekbrains.domain.repository.ISettingsRepository;

public final class SettingsRepository implements ISettingsRepository {

    private final IPrefUtils prefUtils;

    public SettingsRepository(IPrefUtils prefUtils) {
        this.prefUtils = prefUtils;
    }

    @Override
    public Single<Boolean> shouldChangeTheme(String theme) {
        return Single.fromCallable(() -> shouldThemeBeReplaced(theme))
                .observeOn(Schedulers.io());
    }

    @Override
    public Single<String> getCurrentTheme() {
        return Single.fromCallable(prefUtils::currentTheme);
    }

    private boolean shouldThemeBeReplaced(String theme) {
        if (theme.equals(prefUtils.currentTheme())) {
            return false;
        }
        prefUtils.saveCurrentTheme(theme);
        return true;
    }
}