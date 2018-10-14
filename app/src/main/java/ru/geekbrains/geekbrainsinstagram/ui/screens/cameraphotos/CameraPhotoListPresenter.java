package ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos;

import ru.geekbrains.geekbrainsinstagram.base.BaseListPresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.cameraphotos.CameraPhotoListPresenter.CameraPhotoView;

interface CameraPhotoListPresenter extends BaseListPresenter<CameraPhotoView> {

    interface CameraPhotosListView {

        void updatePhotos();

        void updatePhoto(final int position);

        void deletePhoto(final int position);
    }

    interface CameraPhotoView extends BaseListPresenter.RowView {

        void loadImage(final ViewPhotoModel photoModel);

        void setFavorite(final boolean isFavorite);
    }

    void onFavoriteClick(final int position);

    void onDeleteClick(final int position);
}
