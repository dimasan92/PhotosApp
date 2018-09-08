package ru.geekbrains.geekbrainsinstagram.ui.maincontainer;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void setTheme(AppTheme theme);

        void setMainScreenNavigationState(MainScreenNavigationState state);

        void closeApp();
    }

    void readyToSetupTheme();

    void setNavigator(INavigator navigator);

    void viewFirstCreated();

    void backPressed();

    void homeSelected(boolean isFromMainPageNavigationMenu);

    void favoritesSelected(boolean isFromMainPageNavigationMenu);

    void profileSelected(boolean isFromMainPageNavigationMenu);

    void appThemeSelected();
}
