package ru.geekbrains.geekbrainsinstagram.ui.containers;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;

public abstract class BaseContainerPresenterImpl<V extends BaseContainerPresenter.View> extends BasePresenterImpl<V>
        implements BaseContainerPresenter<V> {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;

    protected BaseContainerPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }

    @Override
    public void beforeOnCreate() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(theme),
                        getDefaultErrorHandler()));
    }
}
