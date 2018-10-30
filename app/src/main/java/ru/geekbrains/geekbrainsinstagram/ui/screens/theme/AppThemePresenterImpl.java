package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.settings.ChangeThemeUseCase;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.ui.settings.SettingsScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.screens.theme.AppThemePresenter.ThemeView;

@SettingsScope
public final class AppThemePresenterImpl extends BasePresenterImpl<ThemeView> implements AppThemePresenter {

    private final ChangeThemeUseCase changeThemeUseCase;
    private final Scheduler uiScheduler;
    private final MainNavigator navigator;

    @Inject AppThemePresenterImpl(final ChangeThemeUseCase changeThemeUseCase,
                                  final Scheduler uiScheduler,
                                  final MainNavigator navigator) {
        this.changeThemeUseCase = changeThemeUseCase;
        this.uiScheduler = uiScheduler;
        this.navigator = navigator;
    }

    @Override public void redThemeSelected() {
        shouldThemeChange(AppThemeModel.RED_THEME);
    }

    @Override public void blueThemeSelected() {
        shouldThemeChange(AppThemeModel.BLUE_THEME);
    }

    @Override public void greenThemeSelected() {
        shouldThemeChange(AppThemeModel.GREEN_THEME);
    }

    @Override public void back() {
        view.release();
        navigator.navigateBack();
    }

    private void shouldThemeChange(final AppThemeModel theme) {
        addDisposable(changeThemeUseCase.execute(theme)
                .observeOn(uiScheduler)
                .subscribe(shouldChange -> {
                    if (shouldChange) {
                        view.applyTheme();
                    }
                }, getDefaultErrorHandler()));
    }
}
