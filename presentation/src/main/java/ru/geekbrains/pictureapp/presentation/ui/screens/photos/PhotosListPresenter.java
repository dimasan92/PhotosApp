package ru.geekbrains.pictureapp.presentation.ui.screens.photos;

import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;

import static ru.geekbrains.pictureapp.presentation.ui.screens.photos.PhotosListPresenter.PhotoRowView;

interface PhotosListPresenter extends BaseListPresenter<PhotoRowView> {

    interface PhotoRowView extends BaseListPresenter.RowView {

        void setFavorite(final boolean isFavorite);
    }

    void onFavoriteClick(final int position);

    void onDeleteClick(final int position);

    void onFullClick(final int position);
}
