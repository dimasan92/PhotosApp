package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenter;

public interface MainPresenter extends BaseContainerPresenter<MainPresenter.View> {

    interface View extends BaseContainerPresenter.View {
    }

    void searchSelected(boolean isFromMainPageNavigationMenu);

    void cameraSelected(boolean isFromMainPageNavigationMenu);

    void favoritesSelected(boolean isFromMainPageNavigationMenu);

    void appThemeSelected();
}
