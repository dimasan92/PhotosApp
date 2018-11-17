package ru.geekbrains.pictureapp.presentation.ui.screens.home;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.navigator.HomeNavigator;
import ru.geekbrains.pictureapp.presentation.ui.navigator.Screens;

public interface HomePresenter extends BasePresenter<HomePresenter.HomeView> {

    interface HomeView extends BasePresenter.BaseView {

        void setNavigationState(final Screens screen);

        void closeDrawer();
    }

    void firstCreated();

    void setNavigator(final HomeNavigator navigator);

    void searchSelected(final boolean isFromHomePageNavigationMenu);

    void cameraSelected(final boolean isFromHomePageNavigationMenu);

    void favoritesSelected(final boolean isFromHomePageNavigationMenu);

    void appThemeSelected();

    void back();
}
