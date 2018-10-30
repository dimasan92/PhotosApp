package ru.geekbrains.geekbrainsinstagram.ui.screens.savedsearch;

import ru.geekbrains.geekbrainsinstagram.ui.base.photos.BaseListPresenter;


interface SavedSearchListPresenter extends BaseListPresenter<SavedSearchListPresenter.SavedSearchRowView> {

    interface SavedSearchRowView extends BaseListPresenter.RowView {

        void setFavorite(final boolean isFavorite);
    }

    void onFavoriteClick(final int position);

    void onIoActionClick(final int position);
}
