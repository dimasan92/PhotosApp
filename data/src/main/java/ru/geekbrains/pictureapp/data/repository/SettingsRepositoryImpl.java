package ru.geekbrains.pictureapp.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.pictureapp.data.mapper.ThemeMapper;
import ru.geekbrains.pictureapp.data.util.PrefUtils;
import ru.geekbrains.pictureapp.domain.model.AppThemeModel;
import ru.geekbrains.pictureapp.domain.repository.SettingsRepository;

@Singleton
public final class SettingsRepositoryImpl implements SettingsRepository {

    private final PrefUtils prefUtils;
    private final ThemeMapper themeMapper;
    private final String defaultTheme;

    @Inject
    SettingsRepositoryImpl(final PrefUtils prefUtils, final ThemeMapper themeMapper) {
        this.prefUtils = prefUtils;
        this.themeMapper = themeMapper;
        this.defaultTheme = themeMapper.mapFromModel(AppThemeModel.BLUE_THEME);
    }

    @Override
    public Single<Boolean> changeTheme(final AppThemeModel theme) {
        return Single.fromCallable(() -> shouldThemeBeChanged(theme))
                .subscribeOn(Schedulers.io());
    }

    private boolean shouldThemeBeChanged(final AppThemeModel theme) {
        final String mappedTheme = themeMapper.mapFromModel(theme);
        if (mappedTheme.equals(prefUtils.getCurrentTheme(defaultTheme))) {
            return false;
        }
        prefUtils.saveCurrentTheme(mappedTheme);
        return true;
    }

    @Override
    public Single<AppThemeModel> getCurrentTheme() {
        return Single.just(themeMapper.mapToModel(prefUtils.getCurrentTheme(defaultTheme)));
    }
}