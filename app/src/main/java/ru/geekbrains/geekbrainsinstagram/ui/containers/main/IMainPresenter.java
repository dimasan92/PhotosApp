package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import java.util.List;

import ru.geekbrains.domain.model.AppThemeModel;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screen;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void setTheme(AppThemeModel theme);

        void setMainScreenNavigationState(MainScreenNavigationState state);

        void closeApp();

        void lockDrawer(boolean isLock);

        void closeDrawer();

        void setCurrentScreen(Screen screen);
    }

    void readyToSetupTheme();

    void setNavigator(INavigator navigator);

    void viewFirstCreated();

    void viewRecreated(Screen currentScreen);

    void backPressed(boolean isDrawerOpen);

    void homeSelected(boolean isFromMainPageNavigationMenu);

    void favoritesSelected(boolean isFromMainPageNavigationMenu);

    void profileSelected(boolean isFromMainPageNavigationMenu);

    void appThemeSelected();

    void openFullSizePhoto(List<ViewPhotoModel> photoIds);
}
