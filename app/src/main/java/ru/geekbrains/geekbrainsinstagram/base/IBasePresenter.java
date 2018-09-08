package ru.geekbrains.geekbrainsinstagram.base;

public interface IBasePresenter<V extends IBasePresenter.IView> {

    interface IView {

    }

    void setView(V view);

    void start();

    void stop();
}
