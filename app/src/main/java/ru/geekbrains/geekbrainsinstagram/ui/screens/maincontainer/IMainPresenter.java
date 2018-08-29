package ru.geekbrains.geekbrainsinstagram.ui.screens.maincontainer;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;
import ru.geekbrains.geekbrainsinstagram.model.AppTheme;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void setTheme(AppTheme theme);
    }

    void readyToSetupTheme();
}
