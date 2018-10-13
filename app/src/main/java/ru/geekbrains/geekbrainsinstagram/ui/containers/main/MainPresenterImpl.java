package ru.geekbrains.geekbrainsinstagram.ui.containers.main;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.di.activity.main.MainActivityScope;
import ru.geekbrains.geekbrainsinstagram.ui.containers.BaseContainerPresenterImpl;

@MainActivityScope
public final class MainPresenterImpl extends BaseContainerPresenterImpl<MainPresenter.View>
        implements MainPresenter {

    @Inject
    MainPresenterImpl(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        super(getCurrentThemeUseCase);
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
