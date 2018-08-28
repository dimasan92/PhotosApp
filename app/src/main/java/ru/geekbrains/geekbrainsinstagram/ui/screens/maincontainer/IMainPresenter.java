package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import io.reactivex.functions.Consumer;
import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

    }

    void readyToSetupTheme(Consumer<Integer> themeChanger);
}
