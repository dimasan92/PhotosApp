package ru.geekbrains.geekbrainsinstagram.ui.screens.home;

import javax.inject.Inject;

import ru.geekbrains.geekbrainsinstagram.ui.base.BasePresenterImpl;
import ru.geekbrains.geekbrainsinstagram.di.ui.home.HomeScope;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.BackStackListener;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.HomeNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.MainNavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.geekbrains.geekbrainsinstagram.ui.screens.home.HomePresenter.HomeView;

@HomeScope
public final class HomePresenterImpl extends BasePresenterImpl<HomeView> implements HomePresenter {

    private final MainNavigator mainNavigator;
    private HomeNavigator homeNavigator;

    @Inject HomePresenterImpl(final MainNavigator mainNavigator) {
        this.mainNavigator = mainNavigator;
    }

    @Override public void setNavigator(final HomeNavigator navigator) {
        homeNavigator = navigator;
        homeNavigator.setupBackStackListener(getBackStackListener());
    }

    @Override public void firstCreated() {
        homeNavigator.navigateToSearch();
    }

    @Override public void searchSelected(final boolean isFromHomePageNavigationMenu) {
        if (!isFromHomePageNavigationMenu) {
            view.setNavigationState(Screens.SEARCH_SCREEN);
        } else {
            view.closeDrawer();
        }
        homeNavigator.navigateToSearch();
    }

    @Override public void cameraSelected(boolean isFromHomePageNavigationMenu) {
        if (!isFromHomePageNavigationMenu) {
            view.setNavigationState(Screens.CAMERA_PHOTOS_SCREEN);
        } else {
            view.closeDrawer();
        }
        homeNavigator.navigateToCamera();
    }

    @Override public void favoritesSelected(boolean isFromHomePageNavigationMenu) {
        if (!isFromHomePageNavigationMenu) {
            view.setNavigationState(Screens.FAVORITES_SCREEN);
        } else {
            view.closeDrawer();
        }
        homeNavigator.navigateToFavorites();
    }

    @Override public void appThemeSelected() {
        view.closeDrawer();
        mainNavigator.navigateToAppTheme();
    }

    @Override public void back() {
        homeNavigator.navigateBack();
    }

    private BackStackListener getBackStackListener() {
        return screen -> {
            if (screen == null) {
                mainNavigator.navigateBack();
                return;
            }
            view.setNavigationState(screen);
        };
    }
}
