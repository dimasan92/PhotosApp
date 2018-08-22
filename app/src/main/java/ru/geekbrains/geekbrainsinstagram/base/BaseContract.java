package ru.geekbrains.geekbrainsinstagram.base;

public interface BaseContract {

    interface View {

    }

    interface Presenter<V extends View> {

        void setView(V view);

        void viewIsReady();

        void destroy();
    }
}
