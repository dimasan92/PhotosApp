package ru.geekbrains.geekbrainsinstagram.ui.containers.settings;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public final class SettingsPresenterImpl extends BasePresenter<SettingsPresenter.View> implements SettingsPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;

    private INavigator navigator;

    public SettingsPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }

    @Override
    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void beforeOnCreate() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(theme),
                        getDefaultErrorHandler()));
    }

    @Override
    public void afterOnCreate() {
        navigator.navigateToAppTheme();
    }
}
