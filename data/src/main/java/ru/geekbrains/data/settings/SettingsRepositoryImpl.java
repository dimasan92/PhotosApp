package ru.geekbrains.data.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.ThemeMapper;
import ru.geekbrains.data.util.PrefUtils;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.domain.repository.SettingsRepository;

@Singleton
public final class SettingsRepositoryImpl implements SettingsRepository {

    private final PrefUtils prefUtils;
    private final ThemeMapper themeMapper;
    private final String defaultTheme;

    @Inject
    SettingsRepositoryImpl(PrefUtils prefUtils, ThemeMapper themeMapper) {
        this.prefUtils = prefUtils;
        this.themeMapper = themeMapper;
        this.defaultTheme = themeMapper.domainToData(AppThemeModel.BLUE_THEME);
    }

    @Override
    public Single<Boolean> shouldChangeTheme(final AppThemeModel theme) {
        return Single.fromCallable(() -> shouldThemeBeReplaced(theme))
                .observeOn(Schedulers.io());
    }

    @Override
    public Single<AppThemeModel> getCurrentTheme() {
        return Single.fromCallable(() -> themeMapper.dataToDomain(prefUtils.currentTheme(defaultTheme)));
    }

    private boolean shouldThemeBeReplaced(final AppThemeModel theme) {
        String mappedTheme = themeMapper.domainToData(theme);
        if (mappedTheme.equals(prefUtils.currentTheme(defaultTheme))) {
            return false;
        }
        prefUtils.saveCurrentTheme(mappedTheme);
        return true;
    }
}