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

    private boolean isContentWithMainAction;
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
        isContentWithMainAction = true;
        navigator.navigateToPersonalPhotos();
    }

    @Override
    public void viewRecreated() {
        if (isContentWithMainAction) {
            view.showMainViewAction();
        } else {
            view.hideMainViewAction();
        }
    }

    @Override
    public void personalPhotosSelected() {
        if (!isContentWithMainAction) {
            view.showMainViewAction();
            isContentWithMainAction = true;
        }
        navigator.navigateToPersonalPhotos();
    }

    @Override
    public void appThemeSelected() {
        if (isContentWithMainAction) {
            view.hideMainViewAction();
            isContentWithMainAction = false;
        }
        navigator.navigateToAppTheme();
    }
}
