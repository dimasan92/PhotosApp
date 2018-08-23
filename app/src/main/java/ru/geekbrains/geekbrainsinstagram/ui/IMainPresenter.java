package ru.geekbrains.geekbrainsinstagram.ui;

import ru.geekbrains.geekbrainsinstagram.base.IBasePresenter;

public interface IMainPresenter extends IBasePresenter<IMainPresenter.IView> {

    interface IView extends IBasePresenter.IView {

    }

    int setupTheme();
}
