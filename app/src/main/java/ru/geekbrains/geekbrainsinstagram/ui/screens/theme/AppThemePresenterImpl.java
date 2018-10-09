package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.geekbrains.domain.interactor.settings.ShouldChangeThemeUseCase;
import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.fragment.apptheme.AppThemeFragmentScope;
import ru.geekbrains.geekbrainsinstagram.model.mapper.ViewPhotoModelMapper;

@AppThemeFragmentScope
public final class AppThemePresenterImpl extends BasePresenterImpl<AppThemePresenter.View>
        implements AppThemePresenter {

    private final ShouldChangeThemeUseCase shouldChangeThemeUseCase;
    private final Scheduler uiScheduler;

    @Inject
    AppThemePresenterImpl(ShouldChangeThemeUseCase shouldChangeThemeUseCase, ViewPhotoModelMapper mapper,
                          Scheduler uiScheduler) {
        this.shouldChangeThemeUseCase = shouldChangeThemeUseCase;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void redThemeSelected() {
        shouldThemeChange(AppThemeModel.RED_THEME);
    }

    @Override
    public void blueThemeSelected() {
        shouldThemeChange(AppThemeModel.BLUE_THEME);
    }

    @Override
    public void greenThemeSelected() {
        shouldThemeChange(AppThemeModel.GREEN_THEME);
    }

    private void shouldThemeChange(AppThemeModel theme) {
        addDisposable(shouldChangeThemeUseCase.execute(theme)
                .observeOn(uiScheduler)
                .subscribe(shouldChange -> {
                    if (shouldChange) {
                        view.applyTheme();
                    }
                }, getDefaultErrorHandler()));
    }
}
