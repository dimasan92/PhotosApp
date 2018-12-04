package ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures;

import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter;

import static ru.geekbrains.pictureapp.presentation.ui.screens.savedpictures.SavedPicturesListPresenter.SavedPicturesRowView;


interface SavedPicturesListPresenter extends BaseListPresenter<SavedPicturesRowView> {

    interface SavedPicturesRowView extends BaseListPresenter.RowView {

        void setFavorite(final boolean isFavorite);
    }

    void onFavoriteClick(final int position);

    void onIoActionClick(final int position);

    void onFullClick(final int position);
}
