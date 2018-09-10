package ru.geekbrains.geekbrainsinstagram.ui.maincontainer;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.PresentPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void setTheme(AppThemeModel theme);

        void setMainScreenNavigationState(MainScreenNavigationState state);

        void closeApp();

        void lockDrawer(boolean isLock);
    }

    void readyToSetupTheme();

    void setNavigator(INavigator navigator);

    void viewFirstCreated();

    void viewRecreated();

    void backPressed();

    void homeSelected(boolean isFromMainPageNavigationMenu);

    void favoritesSelected(boolean isFromMainPageNavigationMenu);

    void profileSelected(boolean isFromMainPageNavigationMenu);

    void appThemeSelected();

    void openFullSizePhoto(PresentPhotoModel photo);
}
