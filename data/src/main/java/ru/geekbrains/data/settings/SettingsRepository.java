package ru.geekbrains.data.settings;

import androidx.annotation.StyleRes;
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
    public Single<Boolean> shouldChangeTheme(@StyleRes int themeId) {
        return Single.fromCallable(() -> shouldThemeBeReplaced(themeId))
                .observeOn(Schedulers.io());
    }

    @Override
    public Single<Integer> getCurrentTheme() {
        return Single.fromCallable(prefUtils::currentTheme);
    }

    private boolean shouldThemeBeReplaced(@StyleRes int themeId) {
        if (themeId == prefUtils.currentTheme()) {
            return false;
        }
        prefUtils.saveCurrentTheme(themeId);
        return true;
    }
}