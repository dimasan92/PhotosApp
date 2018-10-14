package ru.geekbrains.geekbrainsinstagram.base;

public interface BaseListPresenter<V extends BaseListPresenter.RowView> {

    interface RowView {
    }

    void bind(int position, V view);

    int getCount();
}
