package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelMapper;
import ru.geekbrains.geekbrainsinstagram.ui.navigator.INavigator;

@ActivityScope
public final class MainPresenter extends BasePresenter<IMainPresenter.IView> implements IMainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;
    private final IPresentModelMapper mapper;

    private INavigator navigator;

    @Inject
    MainPresenter(GetCurrentThemeUseCase getCurrentThemeUseCase, IPresentModelMapper mapper) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
        this.mapper = mapper;
    }

    @Override
    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void readyToSetupTheme() {
        addDisposable(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(mapper.domainToView(theme)),
                        getDefaultErrorHandler()));
    }

    @Override
    public void viewFirstCreated() {
        navigator.navigateToHome();
    }

    @Override
    public void homeSelected() {
        navigator.navigateToHome();
    }

    @Override
    public void favoritesSelected() {
        navigator.navigateToFavorites();
    }

    @Override
    public void profileSelected() {
        navigator.navigateToProfile();
    }

    @Override
    public void appThemeSelected() {
        navigator.navigateToAppTheme();
    }
}
