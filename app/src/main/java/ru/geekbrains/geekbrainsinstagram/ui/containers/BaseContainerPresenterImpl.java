package ru.geekbrains.geekbrainsinstagram.ui.containers;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.terrakok.cicerone.Router;

public abstract class BaseContainerPresenterImpl<V extends BaseContainerPresenter.View> extends BasePresenterImpl<V>
        implements BaseContainerPresenter<V> {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;
    protected final Router router;
    protected final Screens screens;

    protected BaseContainerPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase,
                                         Router router, Screens screens) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
        this.router = router;
        this.screens = screens;
    }

    @Override
    public void beforeOnCreate() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(theme),
                        getDefaultErrorHandler()));
    }
}
