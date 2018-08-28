package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import io.reactivex.functions.Consumer;
import ru.geekbrains.domain.interactor.settings.GetCurrentThemeUseCase;
import ru.geekbrains.geekbrainsinstagram.base.BasePresenter;

public final class MainPresenter extends BasePresenter<IMainPresenter.IView> implements IMainPresenter {

    private final GetCurrentThemeUseCase getCurrentThemeUseCase;

    public MainPresenter(GetCurrentThemeUseCase getCurrentThemeUseCase) {
        this.getCurrentThemeUseCase = getCurrentThemeUseCase;
    }


    @Override
    public void viewIsReady() {
    }

    @Override
    public void readyToSetupTheme(Consumer<Integer> themeChanger) {
        disposables.add(getCurrentThemeUseCase.execute()
                .subscribe(themeChanger));
    }
}
