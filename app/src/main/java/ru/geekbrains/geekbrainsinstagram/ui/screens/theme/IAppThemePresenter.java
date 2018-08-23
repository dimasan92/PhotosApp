package ru.geekbrains.geekbrainsinstagram.ui.screens.theme;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;

public interface IAppThemePresenter extends IBasePresenter<IAppThemePresenter.IView> {

    interface IView extends IBasePresenter.IView {

        void changeTheme();
    }

    void chooseRedTheme();

    void chooseBlueTheme();

    void chooseGreenTheme();
}
