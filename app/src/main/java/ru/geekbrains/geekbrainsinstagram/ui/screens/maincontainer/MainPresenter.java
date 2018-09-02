package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;
import ru.geekbrains.geekbrainsinstagram.di.activity.ActivityScope;
import ru.geekbrains.geekbrainsinstagram.model.mapper.IPresentModelMapper;

@ActivityScope
public final class MainPresenter extends BasePresenter<IMainPresenter.IView> implements IMainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;
    private final IPresentModelMapper mapper;

    @Inject
    MainPresenter(GetCurrentThemeUseCase getCurrentThemeUseCase, IPresentModelMapper mapper) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
        this.mapper = mapper;
    }


    @Override
    public void viewIsReady() {
    }

    @Override
    public void readyToSetupTheme() {
        disposables.add(getCurrentThemeUseCase.execute()
                .subscribe(theme -> view.setTheme(mapper.domainToView(theme))));
    }
}
