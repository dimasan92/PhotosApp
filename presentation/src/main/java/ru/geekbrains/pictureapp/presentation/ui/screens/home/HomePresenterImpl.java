package ru.geekbrains.pictureapp.presentation.ui.screens.home;

import javax.inject.Inject;

import ru.geekbrains.pictureapp.presentation.di.ui.home.HomeScope;
import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenterImpl;
import ru.geekbrains.pictureapp.presentation.ui.navigator.BackStackListener;
import ru.geekbrains.pictureapp.presentation.ui.navigator.HomeNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.MainNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;
import ru.geekbrains.pictureapp.presentation.ui.screens.home.HomePresenter.HomeView;

@HomeScope
public final class HomePresenterImpl extends BasePresenterImpl<HomeView> implements HomePresenter {

    private final MainNavigator mainNavigator;
    private HomeNavigator homeNavigator;

    @Inject
    HomePresenterImpl(final MainNavigator mainNavigator) {
        this.mainNavigator = mainNavigator;
    }

    @Override
    public void setNavigator(final HomeNavigator navigator) {
        homeNavigator = navigator;
        homeNavigator.setupBackStackListener(getBackStackListener());
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

    @Override
    public void firstCreated() {
        homeNavigator.navigateToSearch();
    }

    @Override
    public void searchSelected(final boolean isFromHomePageNavigationMenu) {
        if (isFromHomePageNavigationMenu) {
            view.closeDrawer();
        } else {
            view.setNavigationState(Screens.SEARCH_SCREEN);
        }
        homeNavigator.navigateToSearch();
    }

    @Override
    public void cameraSelected(boolean isFromHomePageNavigationMenu) {
        if (isFromHomePageNavigationMenu) {
            view.closeDrawer();
        } else {
            view.setNavigationState(Screens.CAMERA_PHOTOS_SCREEN);
        }
        homeNavigator.navigateToCamera();
    }

    @Override
    public void favoritesSelected(boolean isFromHomePageNavigationMenu) {
        if (isFromHomePageNavigationMenu) {
            view.closeDrawer();
        } else {
            view.setNavigationState(Screens.FAVORITES_SCREEN);
        }
        homeNavigator.navigateToFavorites();
    }

    @Override
    public void appThemeSelected() {
        view.closeDrawer();
        mainNavigator.navigateToAppTheme();
    }

    @Override
    public void back() {
        homeNavigator.navigateBack();
    }
}
