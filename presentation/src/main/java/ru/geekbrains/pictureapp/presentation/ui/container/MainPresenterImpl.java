package ru.geekbrains.pictureapp.presentation.ui.container;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.pictureapp.presentation.di.ui.MainScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.BackStackListener;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;

@MainScope
public final class MainPresenterImpl extends BasePresenterImpl<MainPresenter.MainView>
        implements MainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;
    private MainNavigator navigator;

    @Inject
    MainPresenterImpl(final GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }

    @Override
    public void setNavigator(final MainNavigator navigator) {
        this.navigator = navigator;
        this.navigator.setupBackStackListener(getBackStackListener());
    }

    @Override
    public void beforeOnCreate() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(view::setTheme,
                        getDefaultErrorHandler()));
    }

    @Override
    public void viewFirstCreated() {
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
