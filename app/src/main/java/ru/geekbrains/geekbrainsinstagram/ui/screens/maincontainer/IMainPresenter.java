package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void setTheme(AppTheme theme);
    }

    void readyToSetupTheme();

    void setNavigator(INavigator navigator);

    void viewFirstCreated();

    void homeSelected();

    void favoritesSelected();

    void profileSelected();

    void personalPhotosSelected();

    void appThemeSelected();
}
