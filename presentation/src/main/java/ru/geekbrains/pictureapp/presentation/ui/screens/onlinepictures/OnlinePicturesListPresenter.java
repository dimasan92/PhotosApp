package ru.geekbrains.pictureapp.presentation.ui.screens.onlinepictures;

import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;

interface OnlinePicturesListPresenter extends BaseListPresenter<OnlinePicturesListPresenter.OnlineSearchRowView> {

    interface OnlineSearchRowView extends BaseListPresenter.RowView {

        void favoriteVisibility(final boolean isVisible);

        void setFavorite(final boolean isFavorite);

        void setSaving(final boolean isSaving);

    }
    void onFavoriteClick(final int position);

    void onIoActionClick(final int position);

    void onFullClick(int adapterPosition);
}
