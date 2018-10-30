package ru.geekbrains.geekbrainsinstagram.ui.container;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.ui.MainScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.BackStackListener;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;

@MainScope
public final class MainPresenterImpl extends BasePresenterImpl<MainPresenter.MainView>
        implements MainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;
    private MainNavigator navigator;

    @Inject MainPresenterImpl(final GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }

    @Override public void setNavigator(final MainNavigator navigator) {
        this.navigator = navigator;
        this.navigator.setupBackStackListener(getBackStackListener());
    }

    @Override public void beforeOnCreate() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(view::setTheme,
                        getDefaultErrorHandler()));
    }

    @Override public void viewFirstCreated() {
        navigator.navigateToHome();
    }

    private BackStackListener getBackStackListener() {
        return screen -> {
            if (screen == null) {
                view.exit();
            }
        };
    }
}
