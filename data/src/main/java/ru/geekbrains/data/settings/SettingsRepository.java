package ru.geekbrains.data.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.data.mapper.IThemeMapper;
import ru.geekbrains.data.util.IPrefUtils;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.domain.repository.ISettingsRepository;

@Singleton
public final class SettingsRepository implements ISettingsRepository {

    private final IPrefUtils prefUtils;
    private final IThemeMapper mapper;

    @Inject
    SettingsRepository(IPrefUtils prefUtils, IThemeMapper mapper) {
        this.prefUtils = prefUtils;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> shouldChangeTheme(AppThemeModel theme) {
        return Single.fromCallable(() -> shouldThemeBeReplaced(theme))
                .observeOn(Schedulers.io());
    }

    @Override
    public Single<AppThemeModel> getCurrentTheme() {
        return Single.fromCallable(() -> mapper.dataToDomain(prefUtils.currentTheme()));
    }

    private boolean shouldThemeBeReplaced(AppThemeModel theme) {
        String mappedTheme = mapper.domainToData(theme);
        if (mappedTheme.equals(prefUtils.currentTheme())) {
            return false;
        }
        prefUtils.saveCurrentTheme(mappedTheme);
        return true;
    }
}