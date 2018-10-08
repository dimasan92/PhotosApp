package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import java.util.List;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screen;

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
        view.setCurrentScreen(Screen.HOME_SCREEN);
        navigator.navigateToHome();
        navigator.setupBackStackListener(backStackListener());
        navigator.setupDrawerUnlockListener(() -> view.lockDrawer(false));
    }

    @Override
    public void viewRecreated(Screen currentScreen) {
        if (currentScreen == Screen.APP_THEME_SCREEN || currentScreen == Screen.FULLSCREEN_PHOTOS_SCREEN) {
            view.setMainScreenNavigationState(MainScreenNavigationState.INVISIBLE_STATE);
        }
    }

    @Override
    public void backPressed(boolean isDrawerOpen) {
        if (isDrawerOpen) {
            view.closeDrawer();
        } else {
            navigator.navigateBack();
        }
    }

    @Override
    public void homeSelected(boolean isFromMainPageNavigationMenu) {
        view.setCurrentScreen(Screen.HOME_SCREEN);
        navigator.navigateToHome();
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.HOME_PAGE_STATE);
        } else {
            view.closeDrawer();
        }
    }

    @Override
    public void favoritesSelected(boolean isFromMainPageNavigationMenu) {
        view.setCurrentScreen(Screen.FAVORITES_SCREEN);
        navigator.navigateToFavorites();
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.FAVORITES_PAGE_STATE);
        } else {
            view.closeDrawer();
        }
    }

    @Override
    public void profileSelected(boolean isFromMainPageNavigationMenu) {
        view.setCurrentScreen(Screen.PROFILE_SCREEN);
        navigator.navigateToProfile();
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.PROFILE_PAGE_STATE);
        } else {
            view.closeDrawer();
        }
    }

    @Override
    public void appThemeSelected() {
        view.setMainScreenNavigationState(MainScreenNavigationState.INVISIBLE_STATE);
        view.setCurrentScreen(Screen.APP_THEME_SCREEN);
        navigator.navigateToAppTheme();
    }

    @Override
    public void openFullSizePhoto(List<ViewPhotoModel> photos) {
        view.setCurrentScreen(Screen.FULLSCREEN_PHOTOS_SCREEN);
        view.setMainScreenNavigationState(MainScreenNavigationState.INVISIBLE_STATE);
        view.lockDrawer(true);
        String[] photoIds = new String[photos.size()];
        for (int i = 0; i < photos.size(); i++) {
            photoIds[i] = photos.get(i).getId();
        }
        navigator.navigateToPhotoDetails(photoIds);
    }

    private INavigator.BackStackListener backStackListener() {
        return screen -> {
            if (screen == null) {
                view.closeApp();
                return;
            }
            view.setCurrentScreen(screen);
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
