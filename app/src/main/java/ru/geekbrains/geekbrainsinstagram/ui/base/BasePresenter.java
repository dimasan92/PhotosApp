package ru.geekbrains.geekbrainsinstagram.ui.base;

public interface BasePresenter<V extends BasePresenter.BaseView> {

    interface BaseView {
    }

    void attachView(V view);

    void start();

    void stop();
}
