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
    MainPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase, Router router, Screens screens) {
        super(getCurrentThemeUseCase, router, screens);
    }
    @Override
    public void searchSelected(boolean isFromMainPageNavigationMenu) {
    }

    @Override
    public void cameraSelected(boolean isFromMainPageNavigationMenu) {
    }

    @Override
    public void favoritesSelected(boolean isFromMainPageNavigationMenu) {
    }

    @Override
    public void appThemeSelected() {
    }
}
