package ru.geekbrains.geekbrainsinstagram.ui;

import javax.inject.Inject;

import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    @Inject
    GetCurrentThemeUseCase getCurrentThemeUseCase;

    @Override
    public void viewIsReady() {
    }

    @Override
    public int setupTheme() {
        return getCurrentThemeUseCase.execute();
    }
}
