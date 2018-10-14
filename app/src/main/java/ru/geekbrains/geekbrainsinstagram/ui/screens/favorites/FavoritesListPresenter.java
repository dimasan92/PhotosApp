package ru.geekbrains.geekbrainsinstagram.ui.screens.favorites;

import ru.geekbrains.geekbrainsinstagram.base.BaseListPresenter;
import ru.geekbrains.geekbrainsinstagram.model.ViewPhotoModel;

import static ru.geekbrains.geekbrainsinstagram.ui.screens.favorites.FavoritesListPresenter.FavoriteView;

interface FavoritesListPresenter extends BaseListPresenter<FavoriteView> {

    interface FavoritesListView {

        void updatePhotos();

        void deletePhoto(final int position);
    }

    interface FavoriteView extends BaseListPresenter.RowView {

        void loadImage(final ViewPhotoModel photoModel);
    }

    void onDeleteFromFavoritesClick(final int position);

    void onDeleteFromDeviceClick(final int position);
}
