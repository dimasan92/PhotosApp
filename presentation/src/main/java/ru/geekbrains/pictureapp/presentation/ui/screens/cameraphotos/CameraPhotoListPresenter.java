package ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos;

import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;

import static ru.geekbrains.pictureapp.presentation.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotoRowView;

interface CameraPhotoListPresenter extends BaseListPresenter<CameraPhotoRowView> {

    interface CameraPhotoRowView extends BaseListPresenter.RowView {

        void setFavorite(final boolean isFavorite);
    }

    void onFavoriteClick(final int position);

    void onDeleteClick(final int position);
}
