package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter;

interface FavoritesListPresenter extends BaseListPresenter<BaseListPresenter.RowView> {

    void onDeleteFromFavoritesClick(final int position);

    void onDeleteFromDeviceClick(final int position);
}
