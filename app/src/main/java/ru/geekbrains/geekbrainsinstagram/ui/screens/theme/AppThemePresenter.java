package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.domain.interactor.settings.ShouldChangeThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class AppThemePresenter extends BasePresenter<IAppThemePresenter.IView>
        implements IAppThemePresenter {

    private final ShouldChangeThemeUseCase shouldChangeThemeUseCase;

    public AppThemePresenter(ShouldChangeThemeUseCase shouldChangeThemeUseCase) {
        this.shouldChangeThemeUseCase = shouldChangeThemeUseCase;
    }
    
    @Override
    public void viewIsReady() {
    }

    @Override
    public void redThemeSelected() {
        shouldThemeChange(AppTheme.RED_THEME);
    }

    @Override
    public void blueThemeSelected() {
        shouldThemeChange(AppTheme.BLUE_THEME);
    }

    @Override
    public void greenThemeSelected() {
        shouldThemeChange(AppTheme.GREEN_THEME);
    }

    private void shouldThemeChange(int theme) {
        disposables.add(shouldChangeThemeUseCase.execute(theme)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shouldChange -> {
                    if (shouldChange) {
                        view.applyTheme();
                    }
                }));
    }
}
