package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenterImpl;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.Screens;
import ru.terrakok.cicerone.Router;

@MainActivityScope
public final class MainPresenterImpl extends BaseContainerPresenterImpl<MainPresenter.View>
        implements MainPresenter {

    @Inject
    MainPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase, Router router) {
        super(getCurrentThemeUseCase, router);
    }

    @Override
    public void searchSelected(boolean isFromMainPageNavigationMenu) {
    }

    @Override
    public void cameraSelected(boolean isFromMainPageNavigationMenu) {
        router.navigateTo(screens.getCameraPhotosScreen());
        if (!isFromMainPageNavigationMenu) {
            view.setMainScreenNavigationState(MainScreenNavigationState.CAMERA_PHOTOS_STATE);
        } else {
            view.closeDrawer();
        }
    }

    @Override
    public void favoritesSelected(boolean isFromMainPageNavigationMenu) {
    }

    @Override
    public void appThemeSelected() {
    }
}
