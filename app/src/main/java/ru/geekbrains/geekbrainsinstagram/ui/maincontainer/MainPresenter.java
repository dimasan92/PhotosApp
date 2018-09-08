package ru.geekbrains.geekbrainsinstagram.ui.maincontainer;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelPhotosMapper;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

@ActivityScope
public final class MainPresenter extends BasePresenter<IMainPresenter.IView> implements IMainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;

    private INavigator navigator;

    @Inject
    MainPresenter(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }

    @Override
    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void readyToSetupTheme() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(theme),
                        getDefaultErrorHandler()));
    }

    @Override
    public void viewFirstCreated() {
        navigator.navigateToHome();
        navigator.setupBackStackListener(backStackListener());
    }

    @Override
    public void backPressed() {
        navigator.navigateBack();
    }

    @Override
    public void homeSelected(boolean isFromMainPageNavigationMenu) {
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.HOME_PAGE_STATE);
        }
        navigator.navigateToHome();
    }

    @Override
    public void favoritesSelected(boolean isFromMainPageNavigationMenu) {
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.FAVORITES_PAGE_STATE);
        }
        navigator.navigateToFavorites();
    }

    @Override
    public void profileSelected(boolean isFromMainPageNavigationMenu) {
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.PROFILE_PAGE_STATE);
        }
        navigator.navigateToProfile();
    }

    @Override
    public void appThemeSelected() {
        view.setMainScreenNavigationState(MainScreenNavigationState.INVISIBLE_STATE);
        navigator.navigateToAppTheme();
    }

    private INavigator.BackStackListener backStackListener() {
        return screen -> {
            if (screen == null) {
                view.closeApp();
                return;
            }
            switch (screen) {
                case HOME_SCREEN:
                    view.setMainScreenNavigationState(MainScreenNavigationState.HOME_PAGE_STATE);
                    break;
                case FAVORITES_SCREEN:
                    view.setMainScreenNavigationState(MainScreenNavigationState.FAVORITES_PAGE_STATE);
                    break;
                case PROFILE_SCREEN:
                    view.setMainScreenNavigationState(MainScreenNavigationState.PROFILE_PAGE_STATE);
                    break;
                case APP_THEME_SCREEN:
                    view.setMainScreenNavigationState(MainScreenNavigationState.INVISIBLE_STATE);
                    break;
            }
        };
    }
}
