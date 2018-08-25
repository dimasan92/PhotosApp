package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class MainPresenter extends BasePresenter<IMainPresenter.IView> implements IMainPresenter {

    @Inject
    GetCurrentThemeUseCase getCurrentThemeUseCase;

    @Override
    public void viewIsReady() {
    }

    @Override
    public void setupTheme(Consumer<Integer> themeChanger) {
        disposables.add(getCurrentThemeUseCase.execute()
                .subscribe(themeChanger));
    }
}
