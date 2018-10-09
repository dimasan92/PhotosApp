package ru.geekbrains.geekbrainsinstagram.base;

public interface BasePresenter<V extends BasePresenter.View> {

    interface View {

    }

    void attachView(V view);

    void start();

    void stop();
}
