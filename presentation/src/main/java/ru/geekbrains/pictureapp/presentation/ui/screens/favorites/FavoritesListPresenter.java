package ru.geekbrains.pictureapp.presentation.ui.screens.favorites;

import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;

interface FavoritesListPresenter extends BaseListPresenter<BaseListPresenter.RowView> {

    void onDeleteFromFavoritesClick(final int position);

    void onDeleteFromDeviceClick(final int position);
}
