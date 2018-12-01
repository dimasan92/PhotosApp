package ru.geekbrains.pictureapp.presentation.ui.screens.details;

import ru.geekbrains.pictureapp.presentation.ui.base.BasePresenter;
import ru.geekbrains.pictureapp.presentation.ui.base.photos.BaseListPresenter.ListView;

public interface DetailsPresenter extends BasePresenter<DetailsPresenter.DetailsView> {

    interface DetailsView extends BasePresenter.BaseView {

        void init(final DetailsListPresenter listPresenter);

        void release();

        void setFavorite(final boolean isFavorite);

        void showDeleteDialog(int position);

        void showErrorAddingToFavoritesMessage();

        void showErrorDeletingFromFavoritesMessage();

        void showErrorDeletingMessage();
    }
    void create(final String[] photos, final int initPosition);

    void attachListView(final ListView listView);

    void onFavoriteClick(final int position);

    void onDeleteClick(final int position);

    void deleteConfirm(int position);

    void back();
}
