package ru.geekbrains.geekbrainsinstagram.base;

public interface BaseListPresenter {

    interface RowView {
    }

    void bind(int position, RowView view);

    int getCount();
}
